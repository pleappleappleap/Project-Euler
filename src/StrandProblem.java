import java.util.stream.IntStream;

public class StrandProblem
{
    public static void main(final String[] args)
    {
        for(int i = 51 ; i < 500 ; i++)
        {
            for(int j = 1 ; j < i ; j++)
            {
                long before = ((j * (j - 1)) / 2);
                long after  = IntStream.range(j + 1, i).sum();

                if(before == after)
                {
                    System.out.println(j);
                    break;
                }
            }
        }
    }
}
