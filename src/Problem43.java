import java.util.Arrays;
import java.util.List;
import java.util.NavigableSet;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Problem43
{
    private static List<Long> PRIMES = Problem7.stream(100).boxed().collect(Collectors.toList());

    private static boolean divisibilityTest(final long number, final int numberOfDigits)
    {
        final long[] subNumbers = subNumbers(number, numberOfDigits);

        for(int i = 1 ; i < subNumbers.length ; i++) if(subNumbers[i] % PRIMES.get(i - 1) != 0) return false;

        return true;
    }

    private static long[] subNumbers(final long number, final int numberOfDigits)
    {
        final long[] digits = digits(number);
        if(digits.length <
           numberOfDigits) throw new IllegalArgumentException("Number of digits specified must be shorter than the original number.");

        final long[] returnme = new long[digits.length - numberOfDigits + 1];
        for(int i = 0 ; i < digits.length - numberOfDigits + 1 ; i++) returnme[i] = assemble(digits, i, i + numberOfDigits - 1);

        return returnme;
    }

    public static long[] digits(final long number)
    {
        final long[] returnme = new long[Problem37.numberOfDigits(number)];
        for(int i = returnme.length - 1 ; i >= 0 ; i--)
        {
            returnme[returnme.length - i - 1] = (number / Problem37.DIGITS[i]) % 10l;
        }
        return returnme;
    }

    public static long assemble(final long[] digits)
    {
        return assemble(digits, 0, digits.length - 1);
    }

    public static long assemble(final long[] digits, final int a, final int b)
    {
        if(a < 0 || b < 0 || a >= digits.length || b >= digits.length) throw new ArrayIndexOutOfBoundsException();
        if(b < a) return assemble(digits, b, a);
        long answer = 0;

        for(int i = a ; i <= b ; i++)
        {
            answer *= 10l;
            answer += digits[i];
        }
        return answer;
    }

    public static void main(final String[] args)
    {
        final long               BEGIN       = System.nanoTime();
        final NavigableSet<Long> pandigitals = Problem38.permute(9, false, true);
        final long               MIDDLE      = System.nanoTime();
        System.out.println(pandigitals.stream().filter(l -> divisibilityTest(l, 3)).mapToLong(l -> l).sum());
        final long END = System.nanoTime();
        System.out.println("" + (MIDDLE - BEGIN) / 1000000.0d + "ms");
        System.out.println("" + (END - MIDDLE) / 1000000.0d + "ms");
    }
}
