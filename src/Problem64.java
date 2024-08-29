import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;

import org.ikickass.util.Pair;

public class Problem64
{
    private static final int         PRECISION = 2;
    private static final MathContext CONTEXT   = new MathContext(PRECISION, RoundingMode.HALF_EVEN);

    public static List<Long> continuedFraction(final BigDecimal x)
    {
        final List<Long> returnme = new ArrayList<>();

        // returnme.add(x.toBigInteger().longValue());
        returnme.addAll(continuedFraction(x, new ArrayList<>(), new LinkedHashSet<>()));
        return returnme;
    }

    private static List<Long> continuedFraction(final BigDecimal x, final List<Long> terms, final Set<BigDecimal> visited)
    {
        final BigInteger current   = x.toBigInteger();
        final BigDecimal remainder = x.subtract(new BigDecimal(current)).setScale(PRECISION, RoundingMode.HALF_EVEN);

        // System.err.println(remainder.equals(BigDecimal.ZERO.setScale(PRECISION,
        // RoundingMode.HALF_EVEN)));
        if(remainder.equals(BigDecimal.ZERO.setScale(PRECISION, RoundingMode.HALF_EVEN))) return terms;

        final BigDecimal reciprocal = BigDecimal.ONE.divide(remainder, CONTEXT);
        final BigInteger truncated  = reciprocal.setScale(0, RoundingMode.HALF_EVEN).toBigInteger();

        System.err.println("" + x + " : " + current + " : " + remainder + " : " + reciprocal + " : " + truncated);

        terms.add(current.longValue());
        if(visited.contains(reciprocal)) return terms;
        else
        {
            visited.add(reciprocal);
            return continuedFraction(reciprocal, terms, visited);
        }
    }

    public static void main(final String[] args)
    {
        LongStream.rangeClosed(2l, 23l)
                  .filter(l -> LongStream.rangeClosed(2l, l).noneMatch(a -> a * a == l))
                  .mapToObj(l -> new Pair<Long, Double>(l, Math.sqrt(l)))
                  .map(pair -> new Pair<Long, List<Long>>(pair.X, continuedFraction(BigDecimal.valueOf(pair.Y))))
                  .map(pair -> pair.toString())
                  .forEach(s -> System.out.println(s));

        System.out.println(continuedFraction(BigDecimal.TWO.sqrt(CONTEXT)));
    }
}
