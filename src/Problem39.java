import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Problem39
{
    private static final int CAP = 1000;

    public static Stream<Problem9.PythagoreanTriple> stream(final int perimeter)
    {
        final List<Problem9.PythagoreanTriple> output = new ArrayList<>();
        for(int i = 1 ; i < perimeter ; i++)
        {
            for(int j = i ; j + i < perimeter ; j++)
            {
                try
                {
                    final Problem9.PythagoreanTriple testMe = Problem9.PythagoreanTriple.builder().setFirst(i).setSecond(j).build();
                    if(testMe.sum() < perimeter) continue;
                    else if(testMe.sum() == perimeter) output.add(testMe);
                    break;
                } catch(ArithmeticException e)
                {
                }
            }
        }
        return output.stream();
    }

    public static void main(final String[] args)
    {
        long max      = 0;
        long maxCount = 0;

        for(int p = 1 ; p <= CAP ; p++)
        {
            final long count = stream(p).count();
            if(count > 0) System.err.println("p = " + p + ", count = " + count);
            if(count > maxCount)
            {
                max      = p;
                maxCount = count;
            }
        }

        System.out.println("max = " + max + ", maxCount = " + maxCount);
    }
}
