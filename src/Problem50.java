import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Problem50
{
    private static final long CAP    = 1000000l;
    private static final long DIGITS = Problem37.numberOfDigits(CAP - 1l);

    public static void main(final String[] args)
    {
        final List<Long>                  primes      =
                Problem7.stream(Problem7.findCount(CAP)).boxed().collect(Collectors.toList());
        final NavigableMap<Long, Integer> primeCounts = new TreeMap<>();
        long                              max         = 0l;
        int                               maxPrimes   = 0;

        for(int i = 0 ; i < primes.size() ; i++)
        {
            long accumulator = 0;
            for(int j = i ; j < primes.size() ; j++)
            {
                accumulator += primes.get(j);

                if(accumulator > CAP)
                {
                    for(int k = j ; k > i ; k--)
                    {
                        accumulator -= primes.get(k);
                        try
                        {
                            if(Problem7.isPrime(accumulator))
                            {
                                if(k - i > maxPrimes)
                                {
                                    max       = accumulator;
                                    maxPrimes = k - i;
                                    break;
                                }
                            } else if(k - i < maxPrimes) break;
                        } finally
                        {
                            primeCounts.put(accumulator, k - i);
                            // System.err.printf("primeCount = %3d, isPrime(%" + DIGITS + "d) = %5s\n",
                            // k - i,
                            // accumulator,
                            // Problem7.isPrime(accumulator));
                        }
                    }
                    break;
                }
            }
        }

        final List<Map.Entry<Long, Integer>> entryList = new ArrayList<>(primeCounts.entrySet());
        int                                  count     = 0;
        for(int i = 1 ; i < entryList.size() ; i++)
        {
            final Map.Entry<Long, Integer> currentEntry  = entryList.get(i);
            final Map.Entry<Long, Integer> previousEntry = entryList.get(i - 1);
            if(currentEntry.getValue() == previousEntry.getValue()) count++;
            else
            {
                System.out.println("previousKey = " + previousEntry.getKey() +
                                   ", previous = " +
                                   previousEntry.getValue() +
                                   ", count = " +
                                   count);
                count = 0;
            }
        }

        System.out.println("max = " + max + ", maxPrimes = " + maxPrimes);
    }
}
