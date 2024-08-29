import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Problem7
{
    private static final List<Long> PRIMES;
    private static final long       CAP = Math.round(Math.sqrt(Long.MAX_VALUE));

    static
    {
        PRIMES = new ArrayList<>();

        PRIMES.add(2l);
        PRIMES.add(3l);
        sieve(20l);
    }

    public static void main(final String[] args)
    {
        System.out.println(sieve(10001));
    }

    private static long get(final int n)
    {
        return PRIMES.get(n);
    }

    public static long getFirst()
    { return PRIMES.get(0); }

    public static long getLast()
    { return PRIMES.get(PRIMES.size() - 1); }

    public static long sieve(final int count)
    {
        if(count < 0) throw new IllegalArgumentException("There are no negative prime numbers.");
        if(count == 0) return getFirst();
        else if(count < PRIMES.size()) return get(count);
        else
        {
            for(long testMe = getLast() + 2l;; testMe += 2l)
            {
                test(testMe);
                if(count < PRIMES.size()) return get(count);
            }
        }
    }

    public static long sieve(final long limit)
    {
        if(limit < 2l) throw new IllegalArgumentException("There are no negative prime numbers.");
        for(long testMe = getLast() + 2l ; testMe <= limit ; testMe += 2l)
        {
            test(testMe);
        }
        for(int i = PRIMES.size() - 1 ; i >= 0 ; i--)
        {
            final long prime = get(i);
            if(prime <= limit) return prime;
        }
        throw new IllegalStateException("We should never reach here.");
    }
    
    private static boolean test(final long n)
    {
        boolean composite = false;
        
        for(final long prime : PRIMES)
        {
            if(prime * prime > n) break;
            else if(n % prime == 0l)
            {
                composite = true;
                break;
            }
        }
        
        if(!composite)
        {
            PRIMES.add(n);
        }
        
        return composite;
    }

    public static LongStream stream()
    {
        return IntStream.iterate(0, i -> i + 1).mapToLong(i -> Problem7.sieve(i));
    }

    public static LongStream stream(final int count)
    {
        sieve(count);
        return IntStream.rangeClosed(0, count).mapToLong(l -> sieve(l));
    }

    public static LongStream stream(final long limit)
    {
        sieve(limit);
        return stream().takeWhile(l -> l <= limit);
    }

    private static long moduloPower(final long X, final long Y, final long p)
    {
        long res = 1;
        long y   = Y;
        long x   = X % p;

        while(y > 0)
        {
            if((y & 1) == 1) res = (res * x) % p;

            y = y >> 1;
            x = (x * x) % p;
        }

        return res;
    }

    private static boolean millerTest(final long D, final long n, final long a)
    {
        long d = D;

        long x = moduloPower(a, d, n);

        if(x == 1 || x == n - 1) return true;

        while(d != n - 1)
        {
            x  = (x * x) % n;
            d *= 2;

            if(x == 1) return false;
            if(x == n - 1) return true;
        }

        return false;
    }

    public static boolean isPrime(final long n)
    {
        if(n <= 1l || n == 4l) return false;
        if(n <= 5l) return true;

        if(PRIMES.contains(n)) return true;

        switch ((int) (n % 10l))
        {
            case 0:
            case 2:
            case 4:
            case 5:
            case 6:
            case 8:
                return false;
        }

        switch ((int) (n % 9l))
        {
            case 3:
            case 6:
            case 9:
                return false;
        }

        long d = n - 1l;

        while(d % 2l == 0) d /= 2l;

        final long[] tests = { 2l, 3l, 5l, 7l, 11l, 13l, 17l };

        for(long a : tests)
        {
            if(!millerTest(d, n, a)) return false;
        }

        PRIMES.add(n);
        return true;
    }
}
