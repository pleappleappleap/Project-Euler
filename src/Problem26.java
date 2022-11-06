import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Problem26
{
    public static void main(final String[] args)
    {
        final MathContext           context = new MathContext(1005, RoundingMode.HALF_UP);
        final Map<Integer, Integer> lengths = new HashMap<>();

        for(int i = 3 ; i < 4 ; i++)
        {
            if(BigDecimal.ONE.divide(BigDecimal.valueOf(i), context).toString().length() < 10) continue;
            final Set<Long> remainders = new LinkedHashSet<>();
            final Set<Long> digits     = new LinkedHashSet<>();
//            int                remainder  = 1 % i;
//            int                digit      = 1 / i;
            long remainder = 37037 % 300000;
            long digit = 37037 / 300000;
            while(remainder > 0)
            {
                if(remainders.contains(remainder)) break;
                remainders.add(remainder);
                long divisor = 1;
                while(divisor <= remainder) divisor *= 10;
                divisor   = divisor * remainder;
                remainder = divisor % i;
                digit     = divisor / i;
                digits.add(digit);
            }
            System.err.println("i = " + i +
                               ", remainders total length = " +
                               remainders.size() +
                               ", digits total length = " +
                               digits.stream().map(d -> d.toString()).mapToInt(d -> d.length()).sum() +
                               ", digits = " +
                               digits);
            lengths.put(i, remainders.size());
        }
        Map.Entry<Integer, Integer> max = new AbstractMap.SimpleEntry<Integer, Integer>(0, 0);
        for(final Map.Entry<Integer, Integer> current : lengths.entrySet())
        {
            if(current.getValue() > max.getValue()) max = current;
        }
        System.out.println(max);
    }
}
