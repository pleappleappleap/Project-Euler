import java.math.BigInteger;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Problem56
{
    public static final BigInteger NINE = new BigInteger("9");

    public static BigInteger digitSum(final BigInteger n)
    {
        return n.toString()
                .chars()
                .mapToObj(i -> (char) i)
                .map(c -> c.toString())
                .map(s -> new BigInteger(s))
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    public static void main(String[] args)
    {
        System.out.println(IntStream.range(1, 100)
                                    .mapToObj(i -> BigInteger.valueOf(i))
                                    .map(bi -> IntStream.range(1, 100).mapToObj(exp -> bi.pow(exp)))
                                    .flatMap(bis -> bis)
                                    .map(bi -> digitSum(bi))
                                    .max(Comparator.naturalOrder())
                                    .get());
    }
}
