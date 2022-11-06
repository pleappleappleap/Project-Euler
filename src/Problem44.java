import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Problem44
{
    private static final Map<Integer, BigInteger> PENTAGONAL_NUMBERS;

    static
    {
        PENTAGONAL_NUMBERS = new HashMap<>();
        PENTAGONAL_NUMBERS.put(1, BigInteger.ONE);
    }

    public static BigInteger pentagonalNumber(final int n)
    {
        if(PENTAGONAL_NUMBERS.containsKey(n)) return PENTAGONAL_NUMBERS.get(n);

        if(n <= 0) throw new IllegalArgumentException("There are no negative pentagonal numbers.");

        final BigInteger N        = new BigInteger(Integer.toString(n));
        final BigInteger returnme = N.multiply(N.multiply(new BigInteger("3")).subtract(BigInteger.ONE)).divide(BigInteger.TWO);
        PENTAGONAL_NUMBERS.put(n, returnme);
        return returnme;
    }

    public static boolean isPentagonal(final BigInteger n)
    {
        for(int i = 1 ; true ; i++)
        {
            final BigInteger current = pentagonalNumber(i);
            if(current.compareTo(n) > 0) break;
        }

        for(final Map.Entry<Integer, BigInteger> currentEntry : PENTAGONAL_NUMBERS.entrySet())
        {
            final BigInteger current = currentEntry.getValue();
            if(current.equals(n)) return true;
        }

        return false;
    }

    public static Stream<BigInteger> stream()
    {
        return IntStream.iterate(1, i -> i + 1).mapToObj(i -> pentagonalNumber(i));
    }

    public static Stream<BigInteger> stream(final int count)
    {
        return IntStream.rangeClosed(1, count).mapToObj(i -> pentagonalNumber(i));
    }

    private static BigInteger difference(final int a, final int b)
    {
        final BigInteger pentagonalA = pentagonalNumber(a);
        final BigInteger pentagonalB = pentagonalNumber(b);
        if(pentagonalA.compareTo(pentagonalB) > 0) return pentagonalA.subtract(pentagonalB);
        else return pentagonalB.subtract(pentagonalA);
    }

    private static BigInteger sum(final int a, final int b)
    {
        return pentagonalNumber(a).add(pentagonalNumber(b));
    }

    public static void main(final String[] args)
    {
        for(int i = 1 ; true ; i++)
        {
            final BigInteger current = pentagonalNumber(i);
            if(!isPentagonal(current)) System.err.println(current);
            for(int j = 1 ; j < i ; j++)
            {
                for(int k = j + 1 ; k < i ; k++)
                {
                    final BigInteger sum = sum(j, k);
                    if(sum.equals(current))
                    {
                        final BigInteger difference = difference(j, k);
                        if(difference.mod(BigInteger.TEN).equals(BigInteger.ZERO)) System.err.println(difference);
                        if(isPentagonal(difference))
                        {
                            System.out.println(difference);
                            return;
                        }
                    } else if(sum.compareTo(current) > 0) break;
                }
            }
        }
    }
}
