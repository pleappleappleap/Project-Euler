import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Problem47
{
    private static final int       CAP      = 10000000;
    private static final int       SQRT_CAP = 3200;
    private static final int[]     PRIMES;
    private static final Integer[] COUNTS   = new Integer[CAP];
    private static long            BEGIN, END;

    static
    {
        BEGIN  = System.nanoTime();
        PRIMES = Problem7.stream(SQRT_CAP).mapToInt(l -> (int) l).toArray();
    }

    public static int countDistinctPrimeFactors(final int n)
    {
        if(COUNTS[n] == null)
        {
            int count = 0;
            for(final int prime : PRIMES)
            {
                if(n % prime == 0) count++;
            }
            COUNTS[n] = count;
        }

        return COUNTS[n];
    }

    public static void main(final String[] args)
    {
        final AtomicInteger currentcount = new AtomicInteger(0);
        IntStream.range(1, CAP).filter(i ->
        {
            if(countDistinctPrimeFactors(i) <= currentcount.get()) return false;
            for(int j = 1 ;
                j <= currentcount.get() ;
                j++) if(countDistinctPrimeFactors(i - j) != countDistinctPrimeFactors(i)) return false;
            currentcount.incrementAndGet();
            return true;
        }).forEach(i -> System.out.printf("%6d : %2d\n", i - currentcount.get() + 1, currentcount.get()));
        END = System.nanoTime();
        System.err.println((END - BEGIN) / 1000000l);
    }
}
