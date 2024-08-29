import java.math.BigInteger;

public class Problem57
{
    private static final int    COUNT  = 10000;
    private static final double FACTOR = Math.log(2) / Math.log(10);
    private static final BigInteger[] POWERS_OF_TEN;
    
    static
    {
        POWERS_OF_TEN = new BigInteger[3829];
        for(int i = 0 ; i < POWERS_OF_TEN.length ; i++)
        {
            POWERS_OF_TEN[i] = BigInteger.TEN.pow(i);
        }
    }

    public static int countDigits(final BigInteger n)
    {
        final int digitCount = (int) (FACTOR * n.bitLength() + 1);
        if(POWERS_OF_TEN[digitCount - 1].compareTo(n) > 0) return digitCount - 1;
        else return digitCount;
    }

    public static int firstTwoDigits(final int n)
    {
        if(n > 999) throw new IllegalArgumentException("We ran away.");
        else if(n < 100) return n;
        else return n / 10;
    }

    public static void main(final String[] args)
    {
        final long BEGIN   = System.nanoTime();
        BigInteger backTwo = BigInteger.TWO;
        BigInteger backOne = BigInteger.valueOf(5);
        int        count   = 0;
        for(int i = 2 ; i <= COUNT ; i++)
        {
            final BigInteger denominator = BigInteger.TWO.multiply(backOne).add(backTwo);
            final BigInteger numerator   = denominator.add(backOne);
            if(countDigits(numerator) > countDigits(denominator))
            {
                count++;
            }
            backTwo = backOne;
            backOne = denominator;
        }

        System.out.println(count);
        final long END = System.nanoTime();
        System.err.println(END - BEGIN);
    }
}
