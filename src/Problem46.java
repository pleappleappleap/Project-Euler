import java.util.stream.LongStream;

public class Problem46
{
    public static void main(final String[] args)
    {
        System.out.println(LongStream.iterate(3l, l -> l + 2).filter(l -> !Problem7.isPrime(l)).filter(l ->
        {
            return !Problem7.stream()
                            .takeWhile(p -> p <= l)
                            .filter(p -> LongStream.rangeClosed(1l, l)
                                                   .map(n -> n * n)
                                                   .map(n -> 2 * n)
                                                   .filter(n -> p + n == l)
                                                   .findFirst()
                                                   .isPresent())
                            .findFirst()
                            .isPresent();
        }).findFirst().getAsLong());
    }
}
