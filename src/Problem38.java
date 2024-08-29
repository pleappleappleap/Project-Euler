import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.ikickass.util.Pair;

public class Problem38
{
    private static Map<Pair<Integer, Integer>, Long> PANDIGITALS;

    static
    {
        PANDIGITALS = new HashMap<>();
        IntStream.rangeClosed(1, 9)
                 .mapToObj(i -> new Pair<Integer, Integer>(1, i))
                 .forEach(p -> PANDIGITALS.put(p, generatePandigital(p)));
    }

    public static boolean isPandigital(final long x, final int a, final int b)
    {
        if(Problem37.numberOfDigits(x) != Math.abs(b - a) + 1) return false;
        else return Long.compare(generatePandigital(a, b), sortDigits(x)) == 0;
    }

    private static long generatePandigital(final Pair<Integer, Integer> pair)
    {
        if(PANDIGITALS.containsKey(pair))
        {
            return PANDIGITALS.get(pair);
        }

        final StringBuilder comparison = new StringBuilder();
        for(int i = pair.X ; i <= pair.Y ; i++)
        {
            comparison.append(i);
        }
        final long answer = Long.valueOf(comparison.toString());
        PANDIGITALS.put(pair, answer);
        return answer;
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

        Pair<Integer, Integer> pair = new Pair<>(a, b);
        return generatePandigital(pair);
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

        output.addAll(permuteStep(0l,
                                  IntStream.rangeClosed(includeZero ? 0 : 1, n)
                                           .boxed()
                                           .collect(TreeSet::new, TreeSet::add, TreeSet::addAll)));
        return output;
    }

    private static List<Long> permuteStep(final long prefix, final NavigableSet<Integer> digits)
    {
        final List<Long> output = new ArrayList<>();

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
                output.addAll(permuteStep(newPrefix, digits));
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

    private static long sequenceMultiply(final long x, final long n)
    {
        return Long.valueOf((LongStream.rangeClosed(1, n)
                                       .map(i -> i * x)
                                       .mapToObj(l -> Long.toString(l))
                                       .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                                       .toString()));
    }

    public static void main(final String[] args)
    {
        final long CAP      = 1000000000l;
        final long SQRT_CAP = 31623l;

        final long BEGIN = System.nanoTime();
        System.out.println(LongStream.rangeClosed(1, SQRT_CAP)
                                     .map(l -> LongStream.iterate(1l, x -> x + 1)
                                                         .map(x -> sequenceMultiply(l, x))
                                                         .takeWhile(n -> n < CAP)
                                                         .filter(n -> isPandigital(n, 1, 9))
                                                         .max()
                                                         .orElse(-1l))
                                     .max()
                                     .getAsLong());
        final long END = System.nanoTime();

        System.out.println("" + (END - BEGIN) / 1000000.0d + "ms");
    }
}
