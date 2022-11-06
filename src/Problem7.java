import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Problem7
{
    private static final List<Long>      PRIMES;
    private static final Map<Long, Long> CAPS;

    private static final long   BORDER     = 113l;
    private static final double BORDER_CAP = 3.508044336d;
    private static final double CAP_LOG    = Math.log(BORDER_CAP);

    static
    {
        PRIMES = new ArrayList<>();
        CAPS   = new HashMap<>();

        PRIMES.add(2l);
        PRIMES.add(3l);
        PRIMES.add(5l);
        PRIMES.add(7l);
        CAPS.put(BORDER, (long) (BORDER * CAP_LOG / Math.log(BORDER)));
    }

    public static void main(final String[] args)
    {
        IntStream.range(0, 1000000000)
                 .mapToLong(i -> sieve(i))
                 .filter(l -> l > 1000000l)
                 .boxed()
                 .findFirst()
                 .ifPresent(l -> System.out.println(l));
    }

    public static long sieve(final int count)
    {
        if(count < 0) throw new IllegalArgumentException("There are no negative prime numbers.");

        sieve(() -> PRIMES.size() <= count);

        return PRIMES.get(count);
    }

    public static long sieve(final long limit)
    {
        if(limit < 2) throw new IllegalArgumentException("There are no negative prime numbers.");

        sieve(() -> PRIMES.get(PRIMES.size() - 1) <= limit);

        for(int i = Math.min((int) findCount(limit), PRIMES.size() - 1) ; i >= 0 ; i--)
        {
            final long current = PRIMES.get(i);
            if(current <= limit) return current;
        }

        throw new AssertionError("We should never be able to reach here.");
    }

    private static void sieve(final BooleanSupplier condition)
    {
        for(long i = PRIMES.get(PRIMES.size() - 1) ; condition.getAsBoolean() ; i++)
        {
            boolean composite = false;
            for(long j : PRIMES)
            {
                if(i % j == 0)
                {
                    composite = true;
                    break;
                }
            }
            if(!composite)
            {
                PRIMES.add(i);
            }
        }
    }

    public static boolean isPrime(final long n)
    {
        if(!PRIMES.contains(n) && (digitSum(n) == 3 || digitSum(n) == 6 || digitSum(n) == 9)) return false;
        else if(n < 2) return false;
        else
        {
            sieve(n);
            return PRIMES.contains(n);
        }
    }

    public static LongStream stream()
    {
        return IntStream.iterate(0, i -> i + 1).mapToLong(i -> Problem7.sieve(i));
    }

    public static LongStream stream(final long count)
    {
        return LongStream.rangeClosed(2, count).map(l -> sieve(l)).distinct();
    }

    public static long findCount(final long limit)
    {
        if(CAPS.containsKey(limit))
        {
            return CAPS.get(limit);
        } else if(limit <= 113l)
        {
            CAPS.put(limit, limit);
            return limit;
        } else
        {
            final long count = (long) (limit * CAP_LOG / Math.log(limit));
            CAPS.put(limit, count);
            return count;
        }
    }

    private static long digitSum(final long n)
    {
        if(n < 0) return digitSum(-n);
        else
        {
            final int subsum =
                    Long.toString(n).chars().mapToObj(c -> (char) c).map(c -> c.toString()).mapToInt(s -> Integer.valueOf(s)).sum();
            if(subsum < 10) return subsum;
            else return digitSum(subsum);
        }
    }
}
