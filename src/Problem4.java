import java.util.stream.IntStream;

public class Problem4
{
	public static void main(String[] args)
	{
		int palindrome = 0;
		
		for(int i = 999 ; i > 0 ; i--)
		{
			for(int j = 999 ; j > 0 ; j--)
			{
				final int current = i * j;
				if(current < palindrome) break;
				else if(isPalindrome(current))
				{
					System.out.println(i + " " + j + " " + current);
					palindrome = current;
				}
			}
		}
		System.out.println(palindrome);
	}
	
	public static boolean isPalindrome(final int checkme)
	{
		return isPalindrome(Integer.toString(checkme));
	}
	
	public static boolean isPalindrome(final String checkme)
	{
		char[] characters = checkme.toCharArray();
		for(int i = 0 ; i <= checkme.length() / 2 ; i++)
		{
			if(characters[i] != characters[characters.length - i - 1])
			{
				return false;
			}
		}
		return true;
	}
	
	public static IntStream stream()
	{
	    return IntStream.iterate(0, i -> i + 1).filter(i -> isPalindrome(i));
	}
	
	public static IntStream stream(final int limit)
	{
	    return IntStream.rangeClosed(0, limit).filter(i -> isPalindrome(i));
	}
}
