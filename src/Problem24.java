import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Problem24
{
    public static final String DIGIT_STRING = "0123456789";
    
    public static Set<String> permute(final long digits)
    {
        return(permute(Long.toString(digits)));
    }

    public static Set<String> permute(final String digits)
    {
        if(digits == null) throw new IllegalArgumentException("Can't permute a null string.");
        return permute(digits.toCharArray());
    }

    public static Set<String> permute(final char[] digits)
    {
        if(digits == null) throw new IllegalArgumentException("Can't permute a null array.");
        final Character[] newDigits = new Character[digits.length];
        for(int i = 0 ; i < digits.length ; i++) newDigits[i] = digits[i];
        return permute(newDigits);
    }

    public static Set<String> permute(final int[] digits)
    {
        final Character[] newDigits = new Character[digits.length];
        for(int i = 0 ; i < digits.length ; i++)
        {
            if(digits[i] < 0 || digits[i] > 9) throw new IllegalArgumentException("Digits can only have one...  digit.");

            newDigits[i] = Integer.toString(digits[i]).toCharArray()[0];
        }
        return permute(newDigits);
    }

    public static Set<String> permute(final Collection<Character> digits)
    {
        if(digits == null) throw new NullPointerException("Can't permute a null list.");
        return permute(digits.toArray(new Character[0]));
    }

    public static Set<String> permute(final Character[] digits)
    {
        if(digits == null) throw new NullPointerException("Can't permute a null array.");

        final Set<String> returnme = new HashSet<>();

        switch (digits.length)
        {
            case 0:
            case 1:
                return new HashSet<>();
            case 2:
                returnme.add(digits[0].toString() + digits[1].toString());
                returnme.add(digits[1].toString() + digits[0].toString());
                return returnme;
            default:
                for(int i = 0 ; i < digits.length ; i++)
                {
                    final int         I             = i;
                    final Character[] filteredArray = new Character[digits.length - 1];
                    for(int j = 0 ; j < digits.length ; j++)
                    {
                        if(j < i) filteredArray[j] = digits[j];
                        else if(j > i) filteredArray[j - 1] = digits[j];
                    }
                    final Set<String> subPermutations = permute(filteredArray);
                    for(final String current : subPermutations)
                    {
                        returnme.add(digits[i].toString() + current);
                    }
                }
        }

        return returnme;
    }

    public static void main(final String[] args)
    {
        int                   i      = 0;
        final TreeSet<String> output = new TreeSet<>(permute(DIGIT_STRING));
        for(final String current : output)
        {
            if(i == 1000000)
            {
                System.out.println(current);
                break;
            } else i++;
        }
    }
}
