import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Problem32
{
    private static final String CAP      = "987654321";
    private static final Long   LONG_CAP = Long.valueOf(CAP);

    private static boolean hasRepeatedCharacters(final Object x)
    {
        final Set<Character> digits = new HashSet<>();

        for(final char d : x.toString().toCharArray())
        {
            if(d == '0') return true;
            else if(digits.contains(d)) return true;
            else digits.add(d);
        }

        return false;
    }

    private static boolean hasRepeatedDigits(final long x)
    {
        return hasRepeatedCharacters(x);
    }

    public static long product(final long a, final long b)
    {
        final long p = a * b;
        if(p < 0 || hasRepeatedDigits(p)) return -1l;
        else return p;
    }

    public static void main(final String[] args)
    {
        final Set<Long> PRODUCTS = new TreeSet<>();
        for(long i = 2 ; i < LONG_CAP / 10l ; i++)
        {
            if(hasRepeatedDigits(i)) continue;
            for(long j = i ; j < LONG_CAP / 10l ; j++)
            {
                if(hasRepeatedDigits(j)) continue;
                else if(Long.toString(i).length() + Long.toString(j).length() > 5) break;

                final long p = product(i, j);
                if(p > 0)
                {
                    final String allDigits = Long.toString(i) + Long.toString(j) + Long.toString(p);
                    if(allDigits.length() > 9) break;
                    else if(allDigits.length() != 9) continue;
                    else if(!hasRepeatedCharacters(allDigits))
                    {
                        System.err.print("i = " + i + ", j = " + j);
                        System.err.println(", p = " + p);
                        PRODUCTS.add(p);
                    }
                }
            }
        }

        System.out.println(PRODUCTS.stream().mapToLong(l -> l).sum());
    }
}
