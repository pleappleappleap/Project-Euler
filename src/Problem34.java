import java.math.BigInteger;
import java.util.stream.LongStream;

public class Problem34
{
    public static long factorialSum(final long x)
    {
        return Long.toString(x)
                   .chars()
                   .mapToObj(i -> (char) i)
                   .map(c -> c.toString())
                   .mapToInt(s -> Integer.valueOf(s))
                   .mapToObj(i -> Problem15.factorial(BigInteger.valueOf(i)))
                   .mapToLong(bi -> bi.longValue())
                   .sum();
    }

    public static void main(final String[] args)
    {
        System.out.println(LongStream.range(3, 1000000l).filter(l -> l == factorialSum(l)).sum());
    }
}
