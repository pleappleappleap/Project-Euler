import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Problem49
{
    public static List<Integer> findEvenlySpaced(final List<Integer> list)
    {
        for(int i = 0 ; i < list.size() ; i++)
        {
            for(int j = i + 1 ; j < list.size() ; j++)
            {
                final int difference = list.get(j) - list.get(i);
                for(int k = j + 1 ; k < list.size() ; k++)
                {
                    if(list.get(k) - list.get(j) == difference) return List.of(list.get(i), list.get(j), list.get(k));
                }
            }
        }

        return List.of();
    }

    public static void main(final String[] args)
    {
        Problem7.stream()
                .takeWhile(l -> l < 10000)
                .mapToObj(l -> Problem24.permute(l))
                .map(ss -> ss.stream().map(s -> Integer.valueOf(s)).collect(Collectors.toSet()))
                .map(is -> is.stream().filter(i -> i > 999).filter(i -> Problem7.isPrime(i)).collect(Collectors.toList()))
                .map(il -> findEvenlySpaced(il))
                .filter(il -> !il.isEmpty())
                .map(il -> new TreeSet<>(il))
                .map(ts -> ts.stream()
                             .map(i -> i.toString())
                             .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append).toString())
                .distinct()
                .forEach(s -> System.out.println(s));
    }
}
