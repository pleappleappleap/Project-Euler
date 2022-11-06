import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Problem15
{
	private static final Map<BigInteger, BigInteger>	FACTORIALS		=	new HashMap<>();
	
	public static BigInteger factorial(final BigInteger n)
	{
		if(n == null) throw new NullPointerException("Cannot calculate factorial for null.");
		else if(n.compareTo(BigInteger.ZERO) < 0)
		{
			throw new IllegalArgumentException("Can only compute factorial for positive numbers.");
		} else if(FACTORIALS.containsKey(n))
		{
			return FACTORIALS.get(n);
		} else if(n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE))  
		{
			FACTORIALS.put(n, BigInteger.ONE);
			return BigInteger.ONE;
		} else
		{
			final BigInteger RETURNME = n.multiply(factorial(n.subtract(BigInteger.ONE)));
			FACTORIALS.put(n, RETURNME);
			return RETURNME;
		}
	}
	
/*	public static BigInteger binomialCoefficient(final BigInteger n, final BigInteger k)
	{
		if(n.compareTo(BigInteger.ZERO) < 0 || k.compareTo(BigInteger.ZERO) < 0)
		{
			throw new IllegalArgumentException("Cannot compute coefficients for negative numbers. n = " + n + ", k = " + k);
		} else if(k.compareTo(n) > 0)
		{
			throw new IllegalArgumentException("k must be less than n + 1.");
		} else if(k.equals(BigInteger.ZERO) || k.equals(n))
		{
			return BigInteger.ONE;
		} else if(k.equals(BigInteger.ONE))
		{
			return n;
		} else {
			// Formula is (n^k)/k!(n-k)!.
//			System.err.print("" + n + "^" + k + " = " + (long) Math.pow(n, k) + ", factorial(" + k + ") * factorial(" + n + " - " + k + ") = " + factorial(k) * factorial(n - k) + " ");
			return factorial(n).divide(factorial(k).multiply(factorial(n.subtract(k))));
		}
	}
	*/
	
	public static BigInteger countPaths(final BigInteger k)
	{
		final BigInteger KFACTORIAL = factorial(k);
		final BigInteger KFACTORIALSQUARED = KFACTORIAL.multiply(KFACTORIAL);
		final BigInteger KTIMESTWO = k.multiply(BigInteger.TWO);

		return factorial(KTIMESTWO).divide(KFACTORIALSQUARED);
	}
	
	public static void main(final String[] args)
	{
		for(BigInteger i = BigInteger.ZERO ; i.compareTo(new BigInteger("100")) <= 0 ; i = i.add(BigInteger.ONE))
		{
			System.out.println(i + ": " + countPaths(i));
		}
	}
}
