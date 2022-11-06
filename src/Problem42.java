import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Problem42
{
    private static final String FILE_NAME = "C:\\Users\\sridhar\\eclipse-workspace\\ProjectEuler\\src\\p042_words.txt";

    public static long triangleNumber(final long n)
    {
        if(n < 1) throw new IllegalArgumentException("There are no negative triangular numbers.");
        else return (n * (n + 1)) / 2;
    }

    public static boolean isTriangleNumber(final long n)
    {
        for(long i = 1 ; (i * (i + 1)) / 2 <= n ; i++)
        {
            if(triangleNumber(i) == n) return true;
        }

        return false;
    }

    public static LongStream stream()
    {
        return LongStream.iterate(0l, l -> l + 1).map(l -> triangleNumber(l));
    }

    public static LongStream stream(final long n)
    {
        return LongStream.rangeClosed(1, n).map(l -> triangleNumber(l));
    }

    private static Stream<String> readFile(final String fileName) throws FileNotFoundException
    {
        final Scanner input = new Scanner(new FileReader(fileName));
        input.useDelimiter(",");

        return input.tokens().map(s -> s.replaceAll("\"*", ""));
    }

    private static long characterValue(final Character c)
    {
        return((long) c - 'A' + 1);
    }

    private static long wordValue(final String word)
    {
        return word.chars().mapToObj(c -> (char) c).map(c -> Character.toUpperCase(c)).mapToLong(c -> characterValue(c)).sum();
    }

    public static void main(final String[] args) throws IOException
    {
        System.out.println(readFile(FILE_NAME).map(s -> new Object[] { s, wordValue(s), isTriangleNumber(wordValue(s)) })
                                              .filter(a -> (boolean) a[2])
                                              .map(a ->
                                              {
                                                  System.err.println(a[0] + " : " + a[1] + " : " + a[2]);
                                                  return a;
                                              })
                                              .count());
    }
}
