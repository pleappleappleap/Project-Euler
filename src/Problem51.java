import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Problem51
{
    private static final String REPLACE_ME = "X";

    private static LongStream substitute(final String n)
    {
        if(n == null || n.isBlank()) throw new IllegalArgumentException("Cannot parse a blank string into a number.");
        else if(!n.chars()
                  .mapToObj(i -> (char) i)
                  .allMatch(c -> (c >= '0' && c <= '9') || c == 'X'))
                                                                      throw new IllegalArgumentException("I can only understand numbers with \"" +
                                                                                                         REPLACE_ME +
                                                                                                         "\"s for substitution");

        return IntStream.rangeClosed(0, 9)
                        .mapToObj(i -> n.replaceAll(REPLACE_ME, Integer.toString(i)))
                        .filter(s -> s.charAt(0) != '0')
                        .mapToLong(s -> Long.valueOf(s));
    }

    private static Stream<String> permute(final String l, final int remaining)
    {
        if(remaining == 1 && l.length() == 1) return Stream.of(REPLACE_ME);
        else if(remaining < 1 | remaining > l.length()) return Stream.of();
        else if(remaining == l.length()) return Stream.of(l.replaceAll(".", REPLACE_ME));

        final List<String> returnme = new ArrayList<>();

        for(int i = 0 ; i < l.length() - remaining + 1 ; i++)
        {
            final String prefix  = l.substring(0, i);
            final String infix   = REPLACE_ME;
            final String postfix = l.substring(i + 1);

            if(remaining > 1) permute(postfix, remaining - 1).forEach(s -> returnme.add(prefix + infix + s));
            else returnme.add(prefix + infix + postfix);
        }

        return returnme.stream();
    }

    public static void main(final String[] args)
    {
        final AtomicInteger count = new AtomicInteger(0);
        Problem7.stream()
                .mapToObj(l -> IntStream.range(1, Problem37.numberOfDigits(l))
                                        .mapToObj(i -> permute(Long.toString(l), i))
                                        .flatMap(ls -> ls)
                                        .filter(s -> s.charAt(0) != '0')
                                        .map(s -> substitute(s).filter(n -> Problem7.isPrime(n)).boxed().toList())
                                        .filter(ll ->
                                        {
                                            if(ll.size() > count.get())
                                            {
                                                count.set(ll.size());
                                                return true;
                                            } else return false;
                                        }))
                .map(lls -> lls.toList())
                .filter(lll -> lll.size() > 0)
                .map(lll -> lll.stream()
                               .map(ll -> new AbstractMap.SimpleEntry<Long,
                                                                      Integer>(ll.stream().mapToLong(l -> l).min().getAsLong(),
                                                                               ll.size())))
                .flatMap(es -> es)
                .forEach(e -> System.out.printf("%3d : %-8d\n", e.getValue(), e.getKey()));
    }
}
