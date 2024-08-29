import java.util.stream.IntStream;

public class Problem52
{
    public static boolean check(final int x)
    {
        final int sortedX  = sorted(x);
        final int sorted2X = sorted(x * 2);
        if(sortedX != sorted2X) return false;
        final int sorted3X = sorted(x * 3);
        if(sortedX != sorted3X) return false;
        final int sorted4X = sorted(x * 4);
        if(sortedX != sorted4X) return false;
        final int sorted5X = sorted(x * 5);
        if(sortedX != sorted5X) return false;
        final int sorted6X = sorted(x * 6);
        if(sortedX != sorted6X) return false;

        return true;
    }

    public static int sorted(final int x)
    {
        return Integer.valueOf(Integer.toString(x)
                                      .chars()
                                      .mapToObj(i -> (char) i)
                                      .map(c -> c.toString())
                                      .sorted()
                                      .collect(StringBuffer::new, (sb, s) -> sb.append(s), (sb1, sb2) -> sb1.append(sb2))
                                      .toString());
    }

    public static void main(final String[] args)
    {
        System.out.println(IntStream.iterate(1, i -> i + 1).filter(i -> check(i)).findFirst().getAsInt());
    }
}
