import java.math.BigInteger;
import java.util.stream.IntStream;

public class Problem55
{
    public static boolean isPalindrome(final BigInteger n)
    {
        final char[] characters = n.toString().toCharArray();
        for(int i = 0 ; i <= characters.length / 2 ; i++)
        {
            if(characters[i] != characters[characters.length - i - 1]) return false;
        }

        return true;
    }

    public static BigInteger reverse(final BigInteger n)
    {
        return new BigInteger(new StringBuilder(n.toString()).reverse().toString());
    }

    public static BigInteger reverse(final int n)
    {
        return reverse(BigInteger.valueOf(n));
    }

    public static boolean isLychrel(final int n)
    {
        BigInteger tryMe = BigInteger.valueOf(n);
        
        for(int i = 0 ; i < 50 ; i++)
        {
            tryMe = tryMe.add(reverse(tryMe));
            if(isPalindrome(tryMe)) return false;
        }
        
        return true;
    }

    public static void main(String[] args)
    {
        System.out.println(IntStream.range(0, 10000).filter(i -> isLychrel(i)).count());
    }

}
