import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Problem28
{
    private static final int SIZE = 1001;
    private static final int LIMIT = SIZE / 2 + 1;

    private static LongStream generateDiagonalNumbers()
    {
        return Stream.iterate(new long[] { 1, 1 }, a -> { a[0] += 2; a[1] = (4 * a[0] * a[0]) - (6 * a[0]) + 6; return a; }).mapToLong(a -> a[1]);
    }

    public static void main(final String[] args)
    {
        final long STARTTIME = System.nanoTime();
        generateDiagonalNumbers().limit(100).forEach(l -> System.out.println(l));
        System.out.println(generateDiagonalNumbers().limit(LIMIT).sum());
        final long ENDTIME = System.nanoTime();
        
        System.out.println("" + ((ENDTIME - STARTTIME) / 1000000) + " ms");
    }
}
