import java.util.ArrayList;
import java.util.List;

public class Problem10 {

	public static void main(String[] args)
	{
		final long LIMIT = 2000000l;
		System.out.println(sieve(LIMIT).stream().reduce(0l, Long::sum));
	}
	
	public static List<Long> sieve(final long limit)
	{
		final List<Long> returnme = new ArrayList<>();

		returnme.add(2l);
		for(long i = 3l ; i <= limit ; i += 2l)
		{
			boolean composite = false;
			for(long j : returnme)
			{
				if(j * j > i) break;
				if(i % j == 0)
				{
					composite = true;
					break;
				}
			}
			if(!composite)
			{
				returnme.add(i);
			}
		}
		return returnme;
	}
}
