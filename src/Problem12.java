import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Problem12
{
    private static final Map<Integer, BigInteger> TRIANGULAR_NUMBERS;

    static
    {
        TRIANGULAR_NUMBERS = new HashMap<>();
        TRIANGULAR_NUMBERS.put(1, BigInteger.ONE);
    }

    public static void main(String[] args)
    {
        int maxCount = 0;
        for(int i = 1 ; i <= Integer.MAX_VALUE ; i++)
        {
            final BigInteger currentTriangularNumber = triangularNumber(i);
            final int        count                   = countFactors(currentTriangularNumber);
            if(count > maxCount)
            {
                maxCount = count;
                System.out.println("i = " + i +
                                   ", currentTriangularNumber = " +
                                   currentTriangularNumber +
                                   " , countFactors(currentTriangularNumber) = " +
                                   count +
                                   ", maxCount = " +
                                   maxCount);
            }
            if(count >= 500) break;
        }
    }

    public static int countFactors(final BigInteger countMe)
    {
        if(countMe.compareTo(BigInteger.ZERO) <= 0) throw new IllegalArgumentException("Can only factor positive integers.");
        else if(countMe.equals(BigInteger.ONE)) return 1;

        int count = 2;
        for(BigInteger i = BigInteger.TWO ; i.multiply(i).compareTo(countMe) <= 0 ; i = i.add(BigInteger.ONE))
        {
            if(countMe.mod(i).equals(BigInteger.ZERO)) count += 2;
            if(countMe.divide(i).equals(i)) count--;
        }
        return count;
    }

    public static BigInteger triangularNumber(final int n)
    {
        if(TRIANGULAR_NUMBERS.containsKey(n)) return TRIANGULAR_NUMBERS.get(n);

        if(n <= 0) throw new IllegalArgumentException("There are no negative pentagonal numbers.");

        final BigInteger N        = new BigInteger(Integer.toString(n));
        final BigInteger returnme = N.multiply(N.add(BigInteger.ONE)).divide(BigInteger.TWO);
        TRIANGULAR_NUMBERS.put(n, returnme);
        return returnme;
    }

    public static boolean isTriangular(final BigInteger n)
    {
        for(int i = 1 ; true ; i++)
        {
            final BigInteger current = triangularNumber(i);
            if(current.compareTo(n) > 0) break;
        }

        for(final Map.Entry<Integer, BigInteger> currentEntry : TRIANGULAR_NUMBERS.entrySet())
        {
            final BigInteger current = currentEntry.getValue();
            if(current.equals(n)) return true;
        }

        return false;
    }

    public static Stream<BigInteger> stream()
    {
        return IntStream.iterate(1, i -> i + 1).mapToObj(i -> triangularNumber(i));
    }

    public static Stream<BigInteger> stream(final int count)
    {
        return IntStream.rangeClosed(1, count).mapToObj(i -> triangularNumber(i));
    }
}
