import java.math.BigInteger;
import java.util.stream.IntStream;

public class Problem48
{
    private static final BigInteger TEN_DIGITS = BigInteger.valueOf(10000000000l);

    public static void main(final String[] args)
    {
        final long BEGIN = System.nanoTime();

        System.out.println(IntStream.rangeClosed(1, 1000)
                                    .mapToObj(i -> BigInteger.valueOf(i).pow(i))
                                    .reduce(BigInteger.ZERO, BigInteger::add)
                                    .mod(TEN_DIGITS));

        final long END = System.nanoTime();

        System.out.println((END - BEGIN) / 1000000l);
    }
}
