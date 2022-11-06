import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Problem45
{
    private static final Map<Integer, BigInteger> HEXAGONAL_NUMBERS;

    static
    {
        HEXAGONAL_NUMBERS = new HashMap<>();
        HEXAGONAL_NUMBERS.put(1, BigInteger.ONE);
    }

    public static BigInteger hexagonalNumber(final int n)
    {
        if(HEXAGONAL_NUMBERS.containsKey(n)) return HEXAGONAL_NUMBERS.get(n);

        if(n <= 0) throw new IllegalArgumentException("There are no negative pentagonal numbers.");

        final BigInteger N        = new BigInteger(Integer.toString(n));
        final BigInteger returnme = N.multiply(N.multiply(BigInteger.TWO).subtract(BigInteger.ONE));
        HEXAGONAL_NUMBERS.put(n, returnme);
        return returnme;
    }

    public static boolean isHexagonal(final BigInteger n)
    {
        for(int i = 1 ; true ; i++)
        {
            final BigInteger current = hexagonalNumber(i);
            if(current.compareTo(n) > 0) break;
        }

        for(final Map.Entry<Integer, BigInteger> currentEntry : HEXAGONAL_NUMBERS.entrySet())
        {
            final BigInteger current = currentEntry.getValue();
            if(current.equals(n)) return true;
        }

        return false;
    }

    public static Stream<BigInteger> stream()
    {
        return IntStream.iterate(1, i -> i + 1).mapToObj(i -> hexagonalNumber(i));
    }

    public static Stream<BigInteger> stream(final int count)
    {
        return IntStream.rangeClosed(1, count).mapToObj(i -> hexagonalNumber(i));
    }

    public static void main(final String[] args)
    {
        Problem45.stream().filter(n -> Problem44.isPentagonal(n)).limit(3l).forEach(n -> System.out.println(n));
    }
}
