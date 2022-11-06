import java.math.BigInteger;

public class Problem16
{
	private static final int POWER = 1000;
	
	public static void main(final String[] args)
	{
		System.out.println(BigInteger.TWO.pow(POWER).toString().chars().mapToObj(c ->  Integer.valueOf(Character.valueOf((char) c).toString())).reduce(0, (a, b) -> a + b));
	}
}
