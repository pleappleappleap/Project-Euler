import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class LogPerformanceTest
{
    private static final int    CAP     = 100000;
    private static final double LOG_10  = Math.log(10d);
    private static final double LOG_2   = Math.log(2d) / LOG_10;
    private static Set<Integer> RANDOMS = null;

    private static void generate()
    {
        final Random generator = new Random();
        RANDOMS = new HashSet<Integer>();
        while(RANDOMS.size() < CAP)
        {
            RANDOMS.add(generator.nextInt());
        }
    }

    public static List<Integer> truncateLogTest()
    {
        if(RANDOMS == null) generate();
        return RANDOMS.stream()
                      .mapToInt(x -> x)
                      .mapToDouble(x -> Math.log(x) / LOG_10)
                      .map(x -> x / LOG_2)
                      .mapToInt(x -> (int) x)
                      .boxed()
                      .toList();
    }

    public static List<Integer> roundingLogTest()
    {
        if(RANDOMS == null) generate();
        return RANDOMS.stream()
                      .mapToInt(x -> x)
                      .mapToDouble(x -> Math.log(x) / LOG_10)
                      .map(x -> x / LOG_2)
                      .mapToInt(x -> (int) Math.round(x))
                      .boxed()
                      .toList();
    }

    public static List<Integer> truncateLengthTest()
    {
        if(RANDOMS == null) generate();
        return RANDOMS.stream()
                      .mapToInt(x -> x.toString().length())
                      .mapToDouble(x -> x / LOG_2)
                      .mapToInt(x -> (int) x)
                      .boxed()
                      .toList();
    }

    public static List<Integer> roundingLengthTest()
    {
        if(RANDOMS == null) generate();
        return RANDOMS.stream()
                      .mapToInt(x -> x.toString().length())
                      .mapToDouble(x -> x / LOG_2)
                      .mapToInt(x -> (int) Math.round(x))
                      .boxed()
                      .toList();
    }

    public static void main(final String[] args)
    {
        final LinkedHashMap<String, Long> times = new LinkedHashMap<>();
        final String[] NAMES = { "START", "generate()", "truncateLogTest()", "roundingLogTest()", "truncateLengthTest()", "roundingLengthTest()" };
        times.put(NAMES[0], System.nanoTime());
        generate();
        times.put(NAMES[1], System.nanoTime() - times.get(NAMES[0]));
        for(int i = 0 ; i < CAP ; i++) truncateLogTest();
        times.put(NAMES[2], System.nanoTime() - times.get(NAMES[1]) - times.get(NAMES[0]));
        for(int i = 0 ; i < CAP ; i++) roundingLogTest();
        times.put(NAMES[3], System.nanoTime() - times.get(NAMES[2]) - times.get(NAMES[0]));
        for(int i = 0 ; i < CAP ; i++) truncateLengthTest();
        times.put(NAMES[4], System.nanoTime() - times.get(NAMES[3]) - times.get(NAMES[0]));
        for(int i = 0 ; i < CAP ; i++) roundingLengthTest();
        times.put(NAMES[5], System.nanoTime() - times.get(NAMES[4]) - times.get(NAMES[0]));
        
        for(final Map.Entry<String, Long> current : times.entrySet()) System.out.printf("%20s : %20f\n", current.getKey(), current.getValue() / 1000000000d);
    }
}
