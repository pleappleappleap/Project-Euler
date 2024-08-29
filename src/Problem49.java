import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Problem49
{
    private final static Set<Long> VISITED_NUMBERS = new HashSet<>();

    private static Set<Long> convert(final Set<String> numbers)
    {
        return numbers.stream().mapToLong(s -> Long.valueOf(s)).boxed().collect(Collectors.toSet());
    }

    public static SortedSet<Long> findEvenlySpaced(final SortedSet<Long> numbers)
    {
        for(final long i : numbers)
        {
            for(final long j : numbers)
            {
                if(j <= i) continue;
                final long difference = j - i;
                for(final long k : numbers)
                {
                    if(k <= j) continue;
                    if(k - j == difference) return new TreeSet<>(Set.of(i, j, k));
                }
            }
        }

        return null;
    }

    public static void main(final String[] args)
    {
        VISITED_NUMBERS.addAll(convert(Problem24.permute(1478l)));

        final long BEGIN = System.nanoTime();
        Problem7.stream(10000l)
                .filter(l -> !VISITED_NUMBERS.contains(l))
                .mapToObj(i -> Problem24.permute(i))
                .map(ss -> convert(ss))
                .map(ls -> new TreeSet<>(ls.stream()
                                           .peek(l -> VISITED_NUMBERS.add(l))
                                           .filter(l -> l > 999l)
                                           .filter(l -> Problem7.isPrime(l))
                                           .collect(Collectors.toSet())))
                .map(ls -> findEvenlySpaced(ls))
                .filter(is -> is != null)
                .map(is -> is.stream()
                             .map(i -> i.toString())
                             .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append))
                .forEach(s -> System.out.println(s));
        final long END = System.nanoTime();
        System.out.println("" + (END - BEGIN) / 1000000.0d + "ms");
    }
}
