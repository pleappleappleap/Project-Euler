import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class Problem29
{
    private static final int CAP = 100;
    
    private static Set<BigInteger> powerTerms(final int amax, final int bmax)
    {
        Set<BigInteger> returnme = new HashSet<>();
        
        for(long a = 2 ; a <= amax ; a++)
        {
            for(int b = 2 ; b <= bmax ; b++)
            {
                returnme.add(BigInteger.valueOf(a).pow(b));
            }
        }
        
        return returnme;
    }
    
    public static void main(final String[] args)
    {
        for(int i = 0 ; i < CAP ; i++) System.out.println(powerTerms(i, i).size());
    }
}
