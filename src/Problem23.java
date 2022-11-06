import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Problem23
{
    public static void main(final String[] args)
    {
        final List<Long> ABUNDANT_NUMBERS = LongStream.range(1, 28124).filter(n -> n < Problem21.divisorSum(n)).boxed().collect(Collectors.toList());
        final Set<Long> ABUNDANT_SUMS = new HashSet<>();
        for(long a : ABUNDANT_NUMBERS)
        {
            for(long b : ABUNDANT_NUMBERS)
            {
                ABUNDANT_SUMS.add(a + b);
            }
        }
        
        System.out.println(LongStream.range(1, 28124).filter(n -> !ABUNDANT_SUMS.contains(n)).boxed().collect(Collectors.toList()));
        System.out.println(LongStream.range(1, 28124).filter(n -> !ABUNDANT_SUMS.contains(n)).sum());
    }
}
