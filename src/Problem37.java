public class Problem37
{
    private static long truncateLeft(final long x)
    {
        final int digitCount = numberOfDigits(x);
        if(digitCount == 1) return 0l;
        else return x % DIGITS[digitCount - 1];
    }

    private static long truncateRight(final long x)
    {
        return x / 10;
    }

    private static long[] DIGITS = { 1l,
                                     10l,
                                     100l,
                                     1000l,
                                     10000l,
                                     100000l,
                                     1000000l,
                                     10000000l,
                                     100000000l,
                                     1000000000l,
                                     10000000000l,
                                     100000000000l,
                                     1000000000000l,
                                     10000000000000l,
                                     100000000000000l,
                                     1000000000000000l,
                                     10000000000000000l,
                                     100000000000000000l,
                                     1000000000000000000l };

    public static boolean allTruncatedPrime(final long n)
    {
        return allLeftTruncatedPrime(n) && allRightTruncatedPrime(n);
    }

    private static boolean allLeftTruncatedPrime(final long n)
    {
        if(n == 0) return true;
        else if(!Problem7.isPrime(n)) return false;
        else return allLeftTruncatedPrime(truncateLeft(n));
    }

    private static boolean allRightTruncatedPrime(final long n)
    {
        if(n == 0) return true;
        else if(!Problem7.isPrime(n)) return false;
        else return allRightTruncatedPrime(truncateRight(n));
    }

    public static int numberOfDigits(final long n)
    {
//      @formatter:off
        return n == Long.MIN_VALUE ? 19 : n < 0l ? numberOfDigits(-n) :
                n < DIGITS[8] ? // 1-8
                  n < DIGITS[4] ? // 1-4
                    n < DIGITS[2] ? // 1-2
                      n < DIGITS[1] ? 1 : 2 : // 1-2
                      n < DIGITS[3] ? 3 : 4 : // 3-4
                    n < DIGITS[6] ? // 5-8
                      n < DIGITS[5] ? 5 : 6 : // 5-6
                      n < DIGITS[7] ? 7 : 8 : // 7-8
                n < DIGITS[16] ? // 9-16
                  n < DIGITS[12] ? // 9-12
                    n < DIGITS[10] ? // 9-10
                      n < DIGITS[9] ? 9 : 10 : // 9-10
                      n < DIGITS[11] ? 11 : 12 : // 11-12
                    n < DIGITS[14] ? // 13-16
                      n < DIGITS[13] ? 13 : 14 : // 13-14
                      n < DIGITS[15] ? 15 : 16 : // 15-16
                      n < DIGITS[17] ? 17 :  // 17-19
                      n < DIGITS[18] ? 18 : 19;
//      @formatter:on
    }

    public static void main(final String[] args)
    {
        System.out.println(Problem7.stream().filter(l -> l > 7l).filter(l -> allTruncatedPrime(l)).limit(11).map(l ->
        {
            System.err.println(l);
            return l;
        }).sum());
    }
}
