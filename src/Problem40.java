import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Problem40
{
    final static long CAP = 1000000l;

    public static IntStream champernowne(final long n)
    {
        if(n <
           1) throw new IllegalArgumentException("Can only generate a Champernowne's constant approximation for a positive number of digits.");

        return LongStream.rangeClosed(1, n)
                         .mapToObj(l -> Long.toString(l))
                         .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                         .toString()
                         .chars()
                         .mapToObj(i -> (char) i)
                         .map(c -> c.toString())
                         .mapToInt(s -> Integer.valueOf(s));
    }
    
    public static void main(final String[] args)
    {
        final List<Long> whichElements = LongStream.iterate(1l, l -> l * 10l).limit(Problem37.numberOfDigits(CAP)).boxed().toList();
        final List<Integer> champernowneElements = champernowne(CAP).boxed().collect(Collectors.toList());
        System.out.println(whichElements.stream().mapToLong(l -> l).mapToInt(l -> (int) l).map(i -> champernowneElements.get(i - 1)).reduce(1, (i1, i2) -> i1 * i2));
    }
}
