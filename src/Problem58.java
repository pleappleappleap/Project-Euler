import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Problem58
{
    private static long upperRight(final long n)
    {
        return (4l * n * n) - (2l * n) + 1l;
    }

    private static long upperLeft(final long n)
    {
        return (4l * n * n) + 1l;
    }

    private static long lowerLeft(final long n)
    {
        return (4l * n * n) + (2l * n) + 1l;
    }

    private static long lowerRight(final long n)
    {
        return (4l * n * n) + (4l * n) + 1l;
    }

    public static LongStream stream()
    {
        return stream(LongStream.iterate(1l, l -> l + 1));
    }

    public static LongStream stream(final long cap)
    {
        return stream(LongStream.rangeClosed(1l, cap));
    }

    private static LongStream stream(final LongStream fromStream)
    {
        return LongStream.concat(LongStream.of(1l),
                                 fromStream.flatMap(l -> LongStream.of(upperRight(l), upperLeft(l), lowerLeft(l))));
    }

    public static Stream<Set<Long>> ulamSpiral()
    {
        return ulamSpiral(LongStream.iterate(1l, l -> l + 1));
    }

    public static Stream<Set<Long>> ulamSpiral(final long cap)
    {
        return ulamSpiral(LongStream.rangeClosed(1l, cap));
    }

    private static Stream<Set<Long>> ulamSpiral(final LongStream fromStream)
    {
        return Stream.concat(Stream.of(Set.of(1l)),
                             fromStream.mapToObj(l -> new LinkedHashSet<>(List.of(upperRight(l),
                                                                                  upperLeft(l),
                                                                                  lowerLeft(l),
                                                                                  lowerRight(l)))));

    }

    public static LongStream primeStream()
    {
        return primeStream(false);
    }

    public static LongStream primeStream(final long cap)
    {
        return primeStream(cap, false);
    }

    public static LongStream primeStream(final boolean deterministic)
    {
        return primeStream(stream(), deterministic);
    }

    public static LongStream primeStream(final long cap, final boolean deterministic)
    {
        return primeStream(stream(cap), deterministic);
    }

    private static LongStream primeStream(final LongStream fromStream, final boolean deterministic)
    {
        return fromStream.filter(l -> Problem7.isPrime(l));
    }

    public static LongStream primeCountStream()
    {
        return primeCountStream(false);
    }

    public static LongStream primeCountStream(final long cap)
    {
        return primeCountStream(cap, false);
    }

    public static LongStream primeCountStream(final boolean deterministic)
    {
        return primeCountStream(LongStream.iterate(0l, l -> l + 1), deterministic);
    }

    public static LongStream primeCountStream(final long cap, final boolean deterministic)
    {
        return primeCountStream(LongStream.range(0l, cap), deterministic);
    }

    private static LongStream primeCountStream(final LongStream fromStream, final boolean deterministic)
    {
        return fromStream.map(l -> primeStream(l, deterministic).count());
    }

    public static void main(String[] args)
    {
        final AtomicLong primeCount = new AtomicLong();
        final AtomicLong totalCount = new AtomicLong();
        final AtomicLong level      = new AtomicLong();

        final long BEGIN = System.nanoTime();
        ulamSpiral().forEach(ls ->
        {
            if(ls.size() == 1)
            {
                primeCount.set(0l);
                totalCount.set(1l);
                level.set(1l);
            } else
            {
                final long primes =
                        primeCount.accumulateAndGet(ls.stream().filter(l -> Problem7.isPrime(l)).count(), (x, y) -> x + y);
                final long total  = totalCount.accumulateAndGet(4l, (x, y) -> x + y);
                final long size   = level.accumulateAndGet(2l, (x, y) -> x + y);
                if((((double) primes) / ((double) total)) <= 0.100d)
                {
                    System.out.println(size);
                    final long END = System.nanoTime();
                    System.out.println("" + (END - BEGIN) / 1000000.0d + "ms");
                    System.exit(0);
                }
            }
        });
    }
}
