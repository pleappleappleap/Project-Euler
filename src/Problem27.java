import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

import org.ikickass.util.Pair;

public class Problem27
{
    private static final int LIMIT = 1000;

    public static void main(final String[] args)
    {
        final long       BEGIN   = System.nanoTime();
        final AtomicLong max     = new AtomicLong(0);
        final AtomicLong product = new AtomicLong(0);
        for(int i = 1 - LIMIT ; i < LIMIT ; i++)
        {
            /*
             * for(int j = -LIMIT ; j <= LIMIT ; j++) { int count = 0; for(long k = 0 ; true
             * ; k++) { final long result = result(i, j, k); if(result < 2 ||
             * !Problem7.isPrime(result)) break; else count++; if(count > max) { max =
             * count; product = i * j; } } }
             */

            final int I = i;
            Problem7.stream(1000l)
                    .mapToInt(l -> (int) l)
                    .mapToObj(J -> new Pair<Integer, Long>(J, countPrimes(I, J)))
                    .filter(pair -> pair.Y > max.get())
                    .forEach(pair ->
                    {
                        max.set(pair.Y);
                        product.set(I * pair.X);
                    });
        }
        final long END = System.nanoTime();

        System.out.println("max = " + max + ", product = " + product + ", time = " + (END - BEGIN) / 1000000.0d + "ms");
    }

    private static long countPrimes(final int a, final int b)
    {
        return LongStream.iterate(0l, l -> l + 1l).map(n -> result(a, b, n)).takeWhile(l -> Problem7.isPrime(l)).count();
    }

    private static long result(final int a, final int b, final long n)
    {
        return (n * n) + (a * n) + b;
    }
}
