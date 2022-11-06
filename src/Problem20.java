import java.math.BigInteger;

public class Problem20
{
    public static void main(final String[] args)
    {
        System.out.println(Problem15.factorial(new BigInteger("100"))
                                    .toString()
                                    .chars()
                                    .mapToObj(d -> new BigInteger(Character.toString(d)))
                                    .reduce(BigInteger.ZERO, BigInteger::add));
    }
}
