import java.math.BigInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Problem63
{
    public static void main(String[] args)
    {
        final long BEGIN = System.nanoTime();
        System.out.println(IntStream.rangeClosed(1, 21)
                                    .mapToLong(a -> Stream.iterate(BigInteger.ONE, b -> b.add(BigInteger.ONE))
                                                          .takeWhile(b -> b.compareTo(BigInteger.TEN) < 0)
                                                          .map(b -> b.pow(a))
                                                          .filter(x -> x.toString().length() == a)
                                                          .count())
                                    .sum());
        final long END = System.nanoTime();

        System.out.println("" + (END - BEGIN) / 1000000.0d + "ms");
    }
}
