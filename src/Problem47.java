import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Problem47
{
    private static final int       CAP      = 10000000;
    private static final int       SQRT_CAP = 3200;
    private static final int[]     PRIMES;
    private static final Integer[] COUNTS   = new Integer[CAP];

    static
    {
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
        final long          BEGIN        = System.nanoTime();
        IntStream.range(1, CAP).filter(i ->
        {
            if(currentcount.get() >= 4) currentcount.set(-1);
            if(countDistinctPrimeFactors(i) <= currentcount.get()) return false;
            for(int j = 1 ;
                j <= currentcount.get() ;
                j++) if(countDistinctPrimeFactors(i - j) != countDistinctPrimeFactors(i)) return false;
            currentcount.incrementAndGet();
            return true;
        })
                 .takeWhile(i -> currentcount.get() > 0)
                 .forEach(i -> System.out.printf("%6d : %2d\n", i - currentcount.get() + 1, currentcount.get()));
        final long END = System.nanoTime();
        System.err.println("" + (END - BEGIN) / 1000000.0d + "ms");
    }
}
