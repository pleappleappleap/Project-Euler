import java.util.Arrays;

import org.ikickass.util.Pair;

public class Problem60
{
    public static boolean checkAll(final long[] numbers)
    {
        return Arrays.stream(numbers).allMatch(x -> Arrays.stream(numbers).filter(y -> x != y).allMatch(y -> checkPair(x, y)));
    }

    public static boolean checkPair(final long x, final long y)
    {
        return Problem7.isPrime(concatenateNumbers(x, y)) && Problem7.isPrime(concatenateNumbers(y, x));
    }

    public static long concatenateNumbers(final long x, final long y)
    {
        return (x * Problem37.DIGITS[Problem37.numberOfDigits(y)]) + y;
    }

    private static long[] concatenate(final long[] a, final long[] b)
    {
        final long[] returnme = new long[a.length + b.length];
        for(int i = 0 ; i < a.length ; i++) returnme[i] = a[i];
        for(int i = a.length ; i < returnme.length ; i++) returnme[i] = b[i - a.length];
        return returnme;
    }

    private static long[] concatenate(final long[] a, final long b)
    {
        final long[] returnme = new long[a.length + 1];
        for(int i = 0 ; i < a.length ; i++) returnme[i] = a[i];
        returnme[a.length] = b;
        return returnme;
    }

    private static long[] concatenate(final long a, final long b)
    {
        return new long[] { a, b };
    }

    public static void main(final String[] args)
    {
        final long CAP = 8400l;

        final long   BEGIN  = System.nanoTime();
        final long[] answer =
                Problem7.stream(CAP)
                        .mapToObj(a -> new Pair<>(a, Problem7.stream(CAP).filter(b -> b > a).filter(b -> checkPair(a, b))))
                        .flatMap(pair -> pair.Y.mapToObj(y -> concatenate(pair.X, y)))
                        .map(array -> new Pair<>(array,
                                                 Problem7.stream(CAP)
                                                         .filter(c -> c > array[1])
                                                         .filter(c -> checkAll(concatenate(array, c)))))
                        .flatMap(pair -> pair.Y.mapToObj(y -> concatenate(pair.X, y)))
                        .map(array -> new Pair<>(array,
                                                 Problem7.stream(CAP)
                                                         .filter(d -> d > array[2])
                                                         .filter(d -> checkAll(concatenate(array, d)))))
                        .flatMap(pair -> pair.Y.mapToObj(y -> concatenate(pair.X, y)))
                        .map(array -> new Pair<>(array,
                                                 Problem7.stream(CAP)
                                                         .filter(e -> e > array[3])
                                                         .filter(e -> checkAll(concatenate(array, e)))))
                        .flatMap(pair -> pair.Y.mapToObj(y -> concatenate(pair.X, y)))
                        .findFirst()
                        .get();
        System.out.println(Arrays.toString(answer) + " " + Arrays.stream(answer).sum());
        final long END = System.nanoTime();

        System.out.println("" + (END - BEGIN) / 1000000.0d + "ms");
    }
}
