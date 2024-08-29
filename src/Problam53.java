import java.math.BigInteger;
import java.util.stream.IntStream;

public class Problam53
{
    private static final BigInteger LIMIT = new BigInteger("1000000");

    public static BigInteger choose(final int n, final int r)
    {
        if(n < 0 || r < 0 || r > n) throw new IllegalArgumentException("Bad numbers.");

        return factorial(n).divide(factorial(r).multiply(factorial(n - r)));
    }

    public static BigInteger factorial(final int x)
    {
        BigInteger answer = BigInteger.ONE;
        for(int i = x ; i > 1 ; i--)
        {
            answer = answer.multiply(BigInteger.valueOf(i));
        }

        return answer;
    }

    public static void main(String[] args)
    {
        System.out.println(IntStream.rangeClosed(1, 100)
                                    .mapToLong(i -> IntStream.rangeClosed(1, i)
                                                             .filter(j -> choose(i, j).compareTo(LIMIT) > 0)
                                                             .count())
                                    .sum());
    }
}
