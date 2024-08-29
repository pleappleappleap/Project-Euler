import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.ikickass.util.Pair;

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
        final List<String> returnme = new ArrayList<>();
        
        if(remaining < 1 || remaining > l.length()) return Stream.of();
        else if(remaining == l.length())
        {
            if(remaining == 1) returnme.add(REPLACE_ME);
            else returnme.add(l.replaceAll(".", REPLACE_ME));
            return returnme.stream();
        }

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

    private static boolean hasThreeRepeatedDigits(final long number)
    {
        int[] foundDigits = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        long current = number;

        while(current > 0)
        {
            final int currentDigit = (int) (current % 10l);
            if(foundDigits[currentDigit] == 2) return true;
            foundDigits[currentDigit]++;
            current /= 10;
        }

        return false;
    }

    public static void main(final String[] args)
    {
        final AtomicInteger count = new AtomicInteger(0);

        final long BEGIN = System.nanoTime();
        System.out.println(Problem7.stream()
                                   .filter(p -> hasThreeRepeatedDigits(p))
                                   .mapToObj(l -> IntStream.range(1, Problem37.numberOfDigits(l))
                                                           .mapToObj(i -> permute(Long.toString(l), i))
                                                           .flatMap(ls -> ls))
                                   .flatMap(s -> s)
                                   .map(s -> substitute(s).filter(n -> Problem7.isPrime(n))
                                                          .boxed()
                                                          .collect(Collectors.summarizingLong(n -> n)))
                                   .map(summary -> new Pair<Long, Long>(summary.getMin(), summary.getCount()))
                                   .filter(pair -> pair.Y == 8l)
                                   .findAny()
                                   .get());
        final long END = System.nanoTime();

        System.out.println("" + (END - BEGIN) / 1000000.0d + "ms");
    }
}
