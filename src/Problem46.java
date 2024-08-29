import java.util.stream.LongStream;

public class Problem46
{
    public static void main(final String[] args)
    {
        final long BEGIN = System.nanoTime();
        System.out.println(LongStream.iterate(3l, l -> l + 2l)
                                     .filter(l -> !Problem7.isPrime(l))
                                     .filter(l -> Problem7.stream(l - 1l)
                                                          .map(p -> l - p)
                                                          .filter(n -> LongStream.rangeClosed(1l, n)
                                                                                 .map(x -> 2 * x * x)
                                                                                 .filter(x -> x == n)
                                                                                 .findAny()
                                                                                 .isPresent())
                                                          .findAny()
                                                          .isEmpty())
                                     .findFirst()
                                     .getAsLong());
        final long END = System.nanoTime();
        System.out.println("" + (END - BEGIN) / 1000000.0d + "ms");
    }
}
