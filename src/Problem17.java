import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class Problem17
{
	private static final Map<Integer, String> DIGIT_NAMES;
	
	static
	{
		final Map<Integer, String> temporaryMap = new HashMap<>();
		temporaryMap.put(0, "");
		temporaryMap.put(1, "ONE");
		temporaryMap.put(2, "TWO");
		temporaryMap.put(3, "THREE");
		temporaryMap.put(4, "FOUR");
		temporaryMap.put(5, "FIVE");
		temporaryMap.put(6, "SIX");
		temporaryMap.put(7, "SEVEN");
		temporaryMap.put(8, "EIGHT");
		temporaryMap.put(9, "NINE");
		temporaryMap.put(10, "TEN");
		temporaryMap.put(11, "ELEVEN");
		temporaryMap.put(12, "TWELVE");
		temporaryMap.put(13, "THIRTEEN");
		temporaryMap.put(14, "FOURTEEN");
		temporaryMap.put(15, "FIFTEEN");
		temporaryMap.put(16, "SIXTEEN");
		temporaryMap.put(17, "SEVENTEEN");
		temporaryMap.put(18, "EIGHTEEN");
		temporaryMap.put(19, "NINETEEN");
		temporaryMap.put(20, "TWENTY");
		temporaryMap.put(30, "THIRTY");
		temporaryMap.put(40, "FORTY");
		temporaryMap.put(50, "FIFTY");
		temporaryMap.put(60, "SIXTY");
		temporaryMap.put(70, "SEVENTY");
		temporaryMap.put(80, "EIGHTY");
		temporaryMap.put(90, "NINETY");
		temporaryMap.put(100, "HUNDRED");
		temporaryMap.put(1000, "THOUSAND");
		temporaryMap.put(1000000, "MILLION");
		temporaryMap.put(1000000000, "BILLION");
		DIGIT_NAMES = Collections.unmodifiableMap(temporaryMap);
	}
	
	public static String intToWords(final int x)
	{
		final StringBuilder output = new StringBuilder();
		final NavigableMap<Integer, CharSequence> staging = new TreeMap<>();
		int processed = x;
		int power = 0;
		
		if(x == 0) return "ZERO";
		else if(x < 0)
		{
			output.append("NEGATIVE");
			processed = -processed;
		}
		
		while(processed > 0)
		{
			staging.put(power++, threeDigitIntToWords(processed % 1000));
			processed /= 1000;
		}
		
		power--;
		
		for(final Integer currentpower : staging.descendingKeySet()) 
		{
			output.append(staging.get(currentpower));
			if(currentpower > 0 && staging.get(currentpower).length() > 0) output.append(DIGIT_NAMES.get((int) Math.pow(1000, currentpower)));
		}
		
		return output.toString();
	}
	
	private static CharSequence threeDigitIntToWords(final int x)
	{
		if(x < 0 || x >= 1000)
		{
			throw new IllegalArgumentException("This function only processes three digit positive integers.");
		}
		
		final StringBuilder output = new StringBuilder();
		
		if(x >= 100)
		{
			output.append(DIGIT_NAMES.get(x / 100));
			output.append(DIGIT_NAMES.get(100));
			if(x % 100 > 0) output.append("AND");
		}
		
		output.append(twoDigitIntToWords(x % 100));
		return output;
	}
	
	private static CharSequence twoDigitIntToWords(final int x)
	{
		if(x < 0 || x >= 100)
		{
			throw new IllegalArgumentException("This function only processes two digit positive integers.");
		}
		
		final StringBuilder output = new StringBuilder();
		
		if(x >= 20)
		{
			output.append(DIGIT_NAMES.get(x / 10 * 10));
			output.append(DIGIT_NAMES.get(x % 10));
		} else
		{
			output.append(DIGIT_NAMES.get(x));
		}
		
		return output;
	}
	
	public static final void main(final String[] args)
	{
		final int total = IntStream.rangeClosed(1, 1000)
			.mapToObj(i -> intToWords(i))
			.mapToInt(w -> w.length())
			.reduce(0, (a, b) -> a + b);
		System.out.println(total);
	}
}
