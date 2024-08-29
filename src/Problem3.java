import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class Problem3
{
    private static final Map<Long, Long> MAX_PRIME_FACTORS = new HashMap<>();
    public static final long             LIMIT             = 600851475143l;

    public static void main(String[] args)
    {
        final long BEGIN = System.nanoTime();
        System.out.println(maxPrimeFactor(LIMIT));
        final long END = System.nanoTime();
        System.out.println("" + (END - BEGIN) / 1000000.0d + "ms");
    }

    public static long maxPrimeFactor(final long limit)
    {
        if(MAX_PRIME_FACTORS.containsKey(limit)) return MAX_PRIME_FACTORS.get(limit);
        final AtomicLong subtotal = new AtomicLong(limit);

        final long answer = Problem7.stream()
                                    .takeWhile(p -> p <= subtotal.get())
                                    .peek(p -> MAX_PRIME_FACTORS.put(p, p))
                                    .filter(p -> subtotal.get() % p == 0)
                                    .peek(p -> { while((subtotal.get() % p == 0l) && (subtotal.get() > p)) subtotal.set(subtotal.get() / p); })
                                    .dropWhile(p -> p != subtotal.get())
                                    .findFirst()
                                    .getAsLong();

        MAX_PRIME_FACTORS.put(limit, answer);
        return answer;
    }

    public static LongStream stream()
    {
        return LongStream.iterate(1l, l -> l + 1).map(l -> maxPrimeFactor(l));
    }

    public static LongStream stream(final long limit)
    {
        return LongStream.rangeClosed(1l, limit).map(l -> maxPrimeFactor(l));
    }
}
