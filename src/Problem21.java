import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

public class Problem21
{
    private static final long            CAP          = 10000l;
    private static final Map<Long, Long> DIVISOR_SUMS = new HashMap<>();

    public static long divisorSum(final long n)
    {
        if(DIVISOR_SUMS.containsKey(n)) return DIVISOR_SUMS.get(n);

        long sum = LongStream.range(1, n).filter(l -> n % l == 0).sum();

        DIVISOR_SUMS.put(n, sum);
        return sum;
    }

    public static void main(final String[] args)
    {
        final long sum = LongStream.range(1, CAP).filter(x -> (x != divisorSum(x))).filter(x -> x == divisorSum(divisorSum(x))).sum();
        System.out.println(sum);
    }
}
