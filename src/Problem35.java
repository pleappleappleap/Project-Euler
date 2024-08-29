import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Problem35
{
    private static final long     CAP        = 1000000l;
    private static final String[] BAD_DIGITS = { "0", "2", "4", "5", "6", "8" };

    public static boolean allIsPrime(final Set<Long> rotations)
    {
        return rotations.stream().allMatch(l -> Problem7.isPrime(l));
    }

    public static boolean allGoodDigits(final long x)
    {
        final String digits = Long.toString(x);
        for(final String digit : BAD_DIGITS) if(digits.contains(digit)) return false;
        return true;
    }

    private static Set<Long> rotations(final long x)
    {
        final Set<Long> returnme = new HashSet<>();
        returnme.add(x);

        final String digits = Long.toString(x);
        for(int i = 1 ; i < digits.length() ; i++)
        {
            final String newDigits = digits.substring(i) + digits.substring(0, i);
            returnme.add(Long.valueOf(newDigits));
        }

        return returnme;
    }

    public static void main(final String[] args)
    {
        final long BEGIN = System.nanoTime();
        System.out.println(Problem7.stream(CAP)
                                   .filter(l -> allGoodDigits(l))
                                   .mapToObj(l -> rotations(l))
                                   .filter(s -> allIsPrime(s))
                                   .count() +
                           2);
        final long END = System.nanoTime();
        System.out.println("" + (END - BEGIN) / 1000000.0d + "ms");
    }
}
