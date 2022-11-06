import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Problem38
{
    public static boolean isPandigital(final long x, final int a, final int b)
    {
        if(Problem37.numberOfDigits(x) != Math.abs(b - a) + 1) return false;
        else return Long.compare(generatePandigital(a, b), sortDigits(x)) == 0;
    }

    private static long generatePandigital(int a, int b)
    {
        if(a < 0 || a > 9 || b < 0 || b > 9)
        {
            throw new IllegalArgumentException("Digits can only have a single...  digit.");
        } else if(b < a)
        {
            final int temp = a;
            a = b;
            b = temp;
        }

        final StringBuilder comparison = new StringBuilder();
        for(int i = a ; i <= b ; i++)
        {
            comparison.append(i);
        }
        return Long.valueOf(comparison.toString());
    }

    public static LongStream stream(final boolean includeZero)
    {
        return IntStream.rangeClosed(1, 9)
                        .mapToObj(i -> permute(i, includeZero))
                        .flatMapToLong(set -> set.stream().mapToLong(l -> l));
    }

    public static LongStream stream()
    {
        return stream(false);
    }

    public static LongStream reverseStream()
    {
        return reverseStream(false);
    }

    public static LongStream reverseStream(final boolean includeZero)
    {
        return stream(includeZero).collect(() -> new TreeSet<>(Comparator.<Long>naturalOrder().reversed()),
                                           TreeSet::add,
                                           TreeSet::addAll)
                                  .stream()
                                  .mapToLong(l -> l);
    }

    public static NavigableSet<Long> permute(final int n)
    {
        return permute(n, false);
    }

    public static NavigableSet<Long> permute(final int n, final boolean reverse)
    {
        return permute(n, reverse, false);
    }

    public static NavigableSet<Long> permute(final int n, final boolean reverse, final boolean includeZero)
    {
        if(n < 1 || n > 9) throw new IllegalArgumentException("Number of digits can only be 1 through 9.");

        NavigableSet<Long> output = null;
        if(reverse) output = new TreeSet<>(Comparator.<Long>naturalOrder().reversed());
        else output = new TreeSet<>();

        if(includeZero)
        {
            output.addAll(permute(0l,
                                  IntStream.rangeClosed(0, n).boxed().collect(TreeSet::new, TreeSet::add, TreeSet::addAll),
                                  reverse));
        } else output.addAll(permute(0l,
                                     IntStream.rangeClosed(1, n).boxed().collect(TreeSet::new, TreeSet::add, TreeSet::addAll),
                                     reverse));
        return output;
    }

    private static NavigableSet<Long> permute(final long prefix, final NavigableSet<Integer> digits, final boolean reverse)
    {
        NavigableSet<Long> output = null;
        if(reverse) output = new TreeSet<>(Comparator.<Long>naturalOrder().reversed());
        else output = new TreeSet<>();

        if(digits.size() == 1)
        {
            output.add(1l);
            return output;
        } else if(Problem37.numberOfDigits(prefix) == digits.size())
        {
            output.add(prefix);
            return output;
        }

        for(final Integer current : digits)
        {
            if(Long.toString(prefix).contains(current.toString())) continue;
            else
            {
                final long newPrefix = prefix * 10 + current;
                output.addAll(permute(newPrefix, digits, reverse));
            }
        }

        return output;
    }

    private static long sortDigits(final long n)
    {
        return Long.valueOf(Long.toString(n)
                                .chars()
                                .mapToObj(c -> (char) c)
                                .map(c -> c.toString())
                                .sorted()
                                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                                .toString());

    }

    private static long sequenceMultiply(final long x, final int n)
    {
        return Long.valueOf((IntStream.rangeClosed(1, n)
                                      .mapToLong(i -> i * x)
                                      .mapToObj(l -> Long.toString(l))
                                      .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                                      .toString()));
    }

    public static void main(final String[] args)
    {
        // final long CAP = 1000000000l;
        // long longest = 0l;
        // for(long i = 1 ; i < 100000l ; i++)
        // {
        // for(int j = 1 ; true ; j++)
        // {
        // final long testMe = sequenceMultiply(i, j);
        // if(testMe >= CAP) break;
        // if(isPandigital(testMe, 1, 9))
        // {
        // if(testMe > longest) longest = testMe;
        // System.err.printf("i = %4d, j = %1d, testMe = %9d, longest = %9d\n", i, j,
        // testMe, longest);
        // }
        // }
        // }
        //
        // System.out.println(longest);

        final long START = System.nanoTime();
        reverseStream().forEach(l -> System.out.println(l));
        final long END = System.nanoTime();

        System.out.println((END - START) / 1000000l);
    }
}
