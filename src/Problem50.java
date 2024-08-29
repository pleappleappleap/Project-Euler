import java.util.List;
import java.util.stream.Collectors;

public class Problem50
{
    private static final long       CAP    = 1000000l;
    private static final List<Long> PRIMES = Problem7.stream(CAP).boxed().collect(Collectors.toList());

    public static void main(final String[] args)
    {
        int  begin  = 0;
        int  length = 0;
        long prime  = 0l;

        final long BEGIN = System.nanoTime();
        for(int i = 0 ; i < PRIMES.size() ; i++)
        {
            long sum = 0;
            for(int j = i ; j < PRIMES.size() ; j++)
            {
                sum += PRIMES.get(j);
                final int  newLength = j - i + 1;
                if(sum > CAP) break;
                else if(newLength <= length) continue;
                else if(Problem7.isPrime(sum))
                {
                    length = newLength;
                    prime = sum;
                    begin = i;
                }

                if(sum > CAP) break;
            }
        }
        final long END = System.nanoTime();

        System.out.println("begin = " + begin + ", length = " + length + ", prime = " + prime);
        System.out.println("" + (END - BEGIN) / 1000000.0d + "ms");
    }
}
