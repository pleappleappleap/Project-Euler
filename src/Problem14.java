import java.util.HashMap;
import java.util.Map;

public class Problem14
{
	private static final Map<Long, Long> COLLATZ_LENGTHS = new HashMap<>();
	private static final long CAP = Long.MAX_VALUE;
	private static long maxCollatzLength = 0l;
	private static long maxCollatzIndex = 0l;
	
	public static long getCollatzLength(final long n)
	{
		if(n < 1) throw new IllegalArgumentException("n cannot be negative.");
		
		long answer;
		
		if(n == 1) answer = 1l;
		else if(COLLATZ_LENGTHS.containsKey(n)) return COLLATZ_LENGTHS.get(n);
		else if(n % 2 == 0) answer = 1l + getCollatzLength(n / 2l);
		else answer = 1l + getCollatzLength((3l * n) + 1l);
		
		COLLATZ_LENGTHS.put(n, answer);
		return answer;
	}
	
	public static void main(final String[] args)
	{
		for(long i = 1; i < CAP; i++)
		{
			try
			{
				final long iCollatzLength = getCollatzLength(i);
				
				if(iCollatzLength > maxCollatzLength)
				{
					maxCollatzLength = iCollatzLength;
					maxCollatzIndex = i;
					System.out.println(maxCollatzIndex + "=" + maxCollatzLength);
				}
			} catch (final StackOverflowError e)
			{
				System.err.println("i=" + i);
				System.err.println(e);
				return;
			}
		}
	}
}
