import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Problem30
{
    private static final Map<Integer, Map<Integer, BigInteger>> POWERS;

    static
    {
        final Map<Integer, Map<Integer, BigInteger>> POWERSTEMP = new TreeMap<>();
        for(int i = 0 ; i < 100 ; i++)
        {
            Map<Integer, BigInteger> ROW = new TreeMap<Integer, BigInteger>();
            for(int j = 0 ; j < 10 ; j++) ROW.put(j, BigInteger.valueOf(j).pow(i));
            POWERSTEMP.put(i, Collections.unmodifiableMap(ROW));
        }
        POWERS = Collections.unmodifiableMap(POWERSTEMP);
    }

    public static BigInteger digitSum(final BigInteger number, final int power)
    {
        return number.toString()
                     .chars()
                     .mapToObj(x -> (char) x)
                     .map(c -> c.toString())
                     .mapToInt(s -> Integer.valueOf(s))
                     .mapToObj(i -> POWERS.get(power).get(i))
                     .reduce(BigInteger.ZERO, BigInteger::add);
    }

    public static final void main(final String[] args)
    {
        for(int power = 0 ; power < 25 ; power++)
        {
            final int              POWER = power;
            final BigInteger       LIMIT = POWERS.get(POWER).get(9).multiply(BigInteger.valueOf(POWER));
            final List<BigInteger> SUMS  = Stream.iterate(BigInteger.ONE, (bi) -> bi.add(BigInteger.ONE))
                                                 .takeWhile(bi -> bi.compareTo(LIMIT) <= 0)
                                                 .filter(bi -> bi.equals(digitSum(bi, POWER)))
                                                 .toList();
            System.out.println(POWER + ":" + SUMS);
        }
    }
}
