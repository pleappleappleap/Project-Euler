import java.util.NoSuchElementException;

public class Problem27
{
    private static final int LIMIT = 1000;
    
    public static void main(final String[] args)
    {
        long max     = 0;
        long product = 0;
        for(int i = 1 - LIMIT ; i < LIMIT ; i++)
        {
            for(int j = -LIMIT ; j <= LIMIT ; j++)
            {
                int count = 0;
                for(long k = 0 ; true ; k++)
                {
                    final long result = result(i, j, k);
                    if(result < 2 || !Problem7.isPrime(result)) break;
                    else count++;
                    if(count > max)
                    {
                        max     = count;
                        product = i * j;
                        System.err.println("i = " + i + ", j = " + j + ", count = " + count);
                    }
                }
            }
        }

        System.out.println("max = " + max + ", product = " + product);
    }

    private static long result(final int a, final int b, final long n)
    {
        return (n * n) + (a * n) + b;
    }
}
