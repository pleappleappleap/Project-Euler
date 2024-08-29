import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Problem30
{
    private static final int        POWER = 5;
    private static final BigInteger LIMIT = new BigInteger("9").pow(POWER).multiply(BigInteger.valueOf(POWER));

    private static final BigInteger[] POWERS;

    static
    {
        POWERS = new BigInteger[10];
        for(int i = 0 ; i < 10 ; i++)
        {
            POWERS[i] = BigInteger.valueOf(i).pow(POWER);
        }
    }

    public static BigInteger powerSum(final BigInteger number, final int power)
    {
        return number.toString()
                     .chars()
                     .mapToObj(x -> (char) x)
                     .map(c -> c.toString())
                     .mapToInt(s -> Integer.valueOf(s))
                     .mapToObj(i -> POWERS[i])
                     .reduce(BigInteger.ZERO, BigInteger::add);
    }

    public static final void main(final String[] args)
    {
        System.out.println(Stream.iterate(BigInteger.ONE, (bi) -> bi.add(BigInteger.ONE))
                                 .takeWhile(bi -> bi.compareTo(LIMIT) <= 0)
                                 .filter(bi -> bi.equals(powerSum(bi, POWER)))
                                 .reduce(BigInteger.ZERO, BigInteger::add));
    }
}
