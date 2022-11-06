import java.util.HashSet;
import java.util.Set;
import java.util.stream.StreamSupport;

public class Problem35
{
    private static final Set<Long> CIRCULAR_PRIMES;

    static
    {
        CIRCULAR_PRIMES = new HashSet<>();
        CIRCULAR_PRIMES.add(2l);
        CIRCULAR_PRIMES.add(3l);
        CIRCULAR_PRIMES.add(5l);
        CIRCULAR_PRIMES.add(7l);
    }

    public static boolean allIsPrime(final long x)
    {
        if(CIRCULAR_PRIMES.contains(x)) return true;
        else if(!filterDigits(x))
        {
            return false;
        } else if(!Problem7.isPrime(x)) return false;
        
        final Set<Long> rotations = rotations(x);
        if(rotations.stream().allMatch(i -> Problem7.isPrime(i)))
        {
            CIRCULAR_PRIMES.addAll(rotations);
            // System.err.println(rotations);
            return true;
        } else return false;
    }

    public static long rotate(final long i)
    {
        final String before = Long.toString(i);
        final String after  = before.substring(1) + before.substring(0, 1);
        return Long.valueOf(after);
    }

    private static Set<Long> rotations(final long x)
    {
        final Set<Long> returnme = new HashSet<>();

        long current = x;
        for(long i = 0 ; i < Long.toString(x).length() ; i++)
        {
            returnme.add(current);
            current = rotate(current);
        }

        return returnme;
    }

    private static boolean filterDigits(final long x)
    {
        return Long.toString(x)
                   .chars()
                   .mapToObj(c -> (char) c)
                   .map(c -> c.toString())
                   .mapToInt(s -> Integer.valueOf(s))
                   .allMatch(i -> i != 0 && i != 2 && i != 4 && i != 5 && i != 6 && i != 8);
    }

    public static void main(final String[] args)
    {
        final long limit = 1000000;

        System.out.println(Problem7.stream(limit).filter(l -> allIsPrime(l)).map(l ->
        {
            System.err.println(l);
            return l;
        }).count());
    }
}
