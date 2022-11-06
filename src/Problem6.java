import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Problem6
{
    private static final Map<Integer, BigInteger> SQUARE_NUMBERS;
    private static final int                      COUNT = 100;

    static
    {
        SQUARE_NUMBERS = new HashMap<>();
        SQUARE_NUMBERS.put(1, BigInteger.ONE);
    }

    public static void main(String[] args)
    {
        final BigInteger SUM_OF_SQUARES = Problem6.stream(COUNT).reduce(BigInteger.ZERO, BigInteger::add);
        final BigInteger SUM            = IntStream.rangeClosed(1, COUNT)
                                                   .mapToObj(i -> Integer.toString(i))
                                                   .map(s -> new BigInteger(s))
                                                   .reduce(BigInteger.ZERO, BigInteger::add);
        final BigInteger SQUARE_OF_SUMS = SUM.multiply(SUM);
        System.out.println(SQUARE_OF_SUMS.subtract(SUM_OF_SQUARES));
    }

    public static BigInteger squareNumber(final int n)
    {
        if(SQUARE_NUMBERS.containsKey(n)) return SQUARE_NUMBERS.get(n);

        if(n <= 0) throw new IllegalArgumentException("There are no negative pentagonal numbers.");

        final BigInteger N        = new BigInteger(Integer.toString(n));
        final BigInteger returnme = N.multiply(N);
        SQUARE_NUMBERS.put(n, returnme);
        return returnme;
    }

    public static boolean isSquare(final BigInteger n)
    {
        for(int i = 1 ; true ; i++)
        {
            final BigInteger current = squareNumber(i);
            if(current.compareTo(n) > 0) break;
        }

        for(final Map.Entry<Integer, BigInteger> currentEntry : SQUARE_NUMBERS.entrySet())
        {
            final BigInteger current = currentEntry.getValue();
            if(current.equals(n)) return true;
        }

        return false;
    }

    public static Stream<BigInteger> stream()
    {
        return IntStream.iterate(1, i -> i + 1).mapToObj(i -> squareNumber(i));
    }

    public static Stream<BigInteger> stream(final int count)
    {
        return IntStream.rangeClosed(1, count).mapToObj(i -> squareNumber(i));
    }

}
