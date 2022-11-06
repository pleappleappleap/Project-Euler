
public class Problem5 {

	public static void main(String[] args)
	{
		for(int i = 2520 ; i > 0 ; i += 2520)
		{
			boolean evenlyDivisible = true;
			for(int j = 1 ; j <= 20 ; j++)
			{
				if(i % j != 0)
				{
					evenlyDivisible = false;
					break;
				}
				evenlyDivisible = true;
			}
			if(evenlyDivisible)
			{
				System.out.println(i);
				return;
			}
		}
	}

}
