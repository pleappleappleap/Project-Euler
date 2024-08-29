import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class Problem22
{
    private static final Pattern DELIMITER = Pattern.compile("[,\"]");
    private static final Pattern WORD = Pattern.compile("[A-Z]*");
    private static final String FILE_NAME = "C:\\Users\\sridhar\\eclipse-workspace\\ProjectEuler\\data\\p022_names.txt";
    
    private static List<String> readFile(final String fileName)
    {
        Scanner in = null;
        
        try
        {
            in = new Scanner(new FileInputStream(fileName));
            in.useDelimiter(DELIMITER);
        } catch(final FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        
        final TreeSet<String> namestemp = new TreeSet<>();
        while(in.hasNext(WORD))
        {
            namestemp.add(in.next(WORD));
        }
        in.close();
        
        return new ArrayList<>(namestemp);
    }
    
    public static long score(final String word)
    {
        if(word == null || word.length() <= 0) return 0;
        
        return word.chars().mapToObj(i -> (char) i).map(c -> c - 'A' + 1).mapToLong(c -> (long) c).reduce(0l, Long::sum);
    }
    
    public static final void main(final String[] args)
    {
        final List<String> WORD_LIST = readFile(FILE_NAME);
        
        long sum = 0l;
        for(int i = 0 ; i < WORD_LIST.size() ; i++)
        {
            sum += i * score(WORD_LIST.get(i));
        }
        
        System.out.println(sum);
    }
}
