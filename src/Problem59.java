import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.IntStream;

import org.ikickass.util.CountMap;

public class Problem59
{
    private static final String  FILE_NAME      = "C:\\Users\\sridhar\\eclipse-workspace\\ProjectEuler\\data\\p059_cipher.txt";
    private static final Scanner INPUT;
    private static final String  PASSWORD       = "exp";
    private static final int[]   PASSWORD_CODES = PASSWORD.chars().toArray();

    static
    {
        Scanner input;
        try
        {
            input = new Scanner(new FileInputStream(FILE_NAME));
            input.useDelimiter(",");
        } catch(final IOException e)
        {
            input = null;
            e.printStackTrace();
        }

        INPUT = input;
    }

    private static IntStream readFile()
    {
        return INPUT.tokens().mapToInt(s -> Integer.valueOf(s));
    }

    public static void main(final String[] args)
    {
        int[]                   message           = readFile().toArray();
        char[]                  messageCharacters = new char[message.length];
        long                    sum               = 0l;
        final CountMap<Integer> counts            = new CountMap<>();

        for(int i = 0 ; i < message.length ; i++)
        {
            counts.increment(message[i]);
            message[i]           ^= PASSWORD_CODES[i % PASSWORD_CODES.length];
            messageCharacters[i]  = (char) message[i];
            sum                  += message[i];
        }

        System.out.println(counts);
        System.out.println(new String(messageCharacters));
        System.out.println(sum);
    }
}
