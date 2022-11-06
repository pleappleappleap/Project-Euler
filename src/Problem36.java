public class Problem36
{
    private static final int CAP = 1000000;

    public static void main(final String[] args)
    {
        System.out.println(Problem4.stream(CAP).filter(i -> isBinaryPalindrome(i)).map(i ->
        {
            System.err.println(i);
            return i;
        }).sum());
    }

    public static boolean isBinaryPalindrome(final int n)
    {
        return Problem4.isPalindrome(Integer.toBinaryString(n));
    }
}
