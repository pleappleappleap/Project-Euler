import java.util.List;
import java.util.NavigableSet;
import java.util.stream.Collectors;

public class Problem43
{
    private static List<Long> PRIMES = Problem7.stream(100).boxed().collect(Collectors.toList());

    private static boolean divisibilityTest(final long number, final int numberOfDigits)
    {
        final long[] subNumbers = subNumbers(number, numberOfDigits);

        for(int i = 1 ; i < subNumbers.length ; i++)
        {
            if((i >= subNumbers.length - 1) &&
               (subNumbers[i] % PRIMES.get(i - 1) == 0)) System.err.println("" + subNumbers[i] + " : " + PRIMES.get(i - 1));
            if((subNumbers[i] % PRIMES.get(i - 1)) != 0) return false;
        }

        return true;
    }

    private static long[] subNumbers(final long number, final int numberOfDigits)
    {
        final String numberString = Long.toString(number);
        if(numberString.length() <
           numberOfDigits) throw new IllegalArgumentException("Number of digits specified must be shorter than the original number.");

        final long[] returnme = new long[numberString.length() - numberOfDigits + 1];
        for(int i = 0 ;
            i < numberString.length() - numberOfDigits + 1 ;
            i++) returnme[i] = Long.valueOf(numberString.substring(i, i + numberOfDigits));

        return returnme;
    }

    public static void main(final String[] args)
    {
        final NavigableSet<Long> pandigitals = Problem38.permute(9, false, true);
        System.out.println(pandigitals.stream().filter(l -> divisibilityTest(l, 3)).mapToLong(l ->
        {
            System.out.println(l);
            return l;
        }).sum());
    }
}
