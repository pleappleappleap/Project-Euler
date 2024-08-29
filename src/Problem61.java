import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.LongStream;

public class Problem61
{
    public static <T extends Comparable<T>> Collector<T, SortedSet<T>, SortedSet<T>> toSortedSet()
    {
        return Collector.<T, SortedSet<T>>of(() -> new TreeSet<>(), (ss, t) -> ss.add(t), (ss1, ss2) ->
        {
            ss1.addAll(ss2);
            return ss1;
        });
    }

    public static LongStream triangleStream()
    {
        return LongStream.iterate(1l, l -> l + 1l).map(l -> (l * (l + 1l)) / 2l);
    }

    public static LongStream squareStream()
    {
        return LongStream.iterate(1l, l -> l + 1).map(l -> l * l);
    }

    public static LongStream pentagonStream()
    {
        return LongStream.iterate(1l, l -> l + 1).map(l -> (l * ((3 * l) - 1l)) / 2l);
    }

    public static LongStream hexagonStream()
    {
        return LongStream.iterate(1l, l -> l + 1).map(l -> l * ((2 * l) - 1));
    }

    public static LongStream heptagonStream()
    {
        return LongStream.iterate(1l, l -> l + 1).map(l -> (l * ((l * 5l) - 3l)) / 2l);
    }

    public static LongStream octagonStream()
    {
        return LongStream.iterate(1l, l -> l + 1).map(l -> l * ((3 * l) - 2l));
    }

    private static long firstTwo(final long x)
    {
        if(x < 1000 || x > 9999)
        {
            throw new IllegalArgumentException("firstTwo only works for four digit numbers.");
        } else return x / 100l;
    }

    private static long lastTwo(final long x)
    {
        if(x < 1000 || x > 9999)
        {
            throw new IllegalArgumentException("lastTwo only works for four digit numbers");
        } else return x % 100l;
    }

    private static LongStream matchesLast(final long a, final long[] candidates)
    {
        return Arrays.stream(candidates).filter(b -> firstTwo(b) == lastTwo(a));
    }

    private static boolean matchesAny(final long a, final long[] candidates)
    {
        return Arrays.stream(candidates).anyMatch(b -> b == a);
    }

    private static long[] append(final long a, final long[] A)
    {
        long[] returnme = new long[A.length + 1];
        for(int i = 0 ; i < A.length ; i++) returnme[i] = A[i];
        returnme[A.length] = a;
        return returnme;
    }

    private static boolean checkAll(final long[] numbers)
    {
        int i     = 0;
        int count = 0;
        while(count < numbers.length)
        {
            final int j = nextIndex(numbers, i);

            if(j == -1) return false;
            else
            {
                if(j == 0)
                {
                    if(count == numbers.length - 1) return true;
                    else return false;
                }
            }
            i = j;
            count++;
        }
        return false;
    }

    private static int nextIndex(final long[] numbers, final int firstIndex)
    {
        for(int i = 0 ; i < numbers.length ; i++)
        {
            if(firstTwo(numbers[i]) == lastTwo(numbers[firstIndex])) return i;
        }
        return -1;
    }

    public static void main(String[] args)
    {
        final long[] triangles = triangleStream().filter(l -> l > 999l)
                                                 .takeWhile(l -> l < 10000l)
                                                 .filter(l -> ((l % 100l) / 10l) != 0l)
                                                 .filter(l -> l % 100l != l / 100l)
                                                 .filter(l -> firstTwo(l) != lastTwo(l))
                                                 .sorted()
                                                 .toArray();
        final long[] squares   = squareStream().filter(l -> l > 999l)
                                               .takeWhile(l -> l < 10000l)
                                               .filter(l -> ((l % 100l) / 10l) != 0l)
                                               .filter(l -> l % 100l != l / 100l)
                                               .filter(l -> firstTwo(l) != lastTwo(l))
                                               .sorted()
                                               .toArray();
        final long[] pentagons = pentagonStream().filter(l -> l > 999l)
                                                 .takeWhile(l -> l < 10000l)
                                                 .filter(l -> ((l % 100l) / 10l) != 0l)
                                                 .filter(l -> l % 100l != l / 100l)
                                                 .filter(l -> firstTwo(l) != lastTwo(l))
                                                 .sorted()
                                                 .toArray();
        final long[] hexagons  = hexagonStream().filter(l -> l > 999l)
                                                .takeWhile(l -> l < 10000l)
                                                .filter(l -> ((l % 100l) / 10l) != 0l)
                                                .filter(l -> l % 100l != l / 100l)
                                                .filter(l -> firstTwo(l) != lastTwo(l))
                                                .sorted()
                                                .toArray();
        final long[] heptagons = heptagonStream().filter(l -> l > 999l)
                                                 .takeWhile(l -> l < 10000l)
                                                 .filter(l -> ((l % 100l) / 10l) != 0l)
                                                 .filter(l -> l % 100l != l / 100l)
                                                 .filter(l -> firstTwo(l) != lastTwo(l))
                                                 .sorted()
                                                 .toArray();
        final long[] octagons  = octagonStream().filter(l -> l > 999l)
                                                .takeWhile(l -> l < 10000l)
                                                .filter(l -> ((l % 100l) / 10l) != 0l)
                                                .filter(l -> l % 100l != l / 100l)
                                                .filter(l -> firstTwo(l) != lastTwo(l))
                                                .sorted()
                                                .toArray();

        final long[][] numbers = { triangles, squares, pentagons, hexagons, heptagons, octagons };

        final long BEGIN = System.nanoTime();
        long       last  = BEGIN;
        outerloop: for(int i = 0 ; i < triangles.length ; i++)
        {
            final long   NOW           = System.nanoTime();
            final double LASTBATCHTIME = (NOW - last) / 1000000.0d;
            final double ELAPSED       = (NOW - BEGIN) / 1000000.0d;
            System.out.println("" + LASTBATCHTIME + "ms");
            System.out.println("" + ELAPSED + "ms");
            last = NOW;
            System.out.println("Estimated total " + ((triangles.length * LASTBATCHTIME) / 60000.0d) + "m");
            final long[] arrayA = { triangles[i] };
            for(int j = 0 ; j < squares.length ; j++)
            {
                System.err.println("" + triangles.length + ":" + squares.length + " - " + i + ":" + j);
                final long[] arrayB = append(squares[j], arrayA);
                if(checkAll(arrayB)) continue;
                for(int k = 0 ; k < pentagons.length ; k++)
                {
                    final long[] arrayC = append(pentagons[k], arrayB);
                    if(checkAll(arrayC)) continue;
                    for(int l = 0 ; l < hexagons.length ; l++)
                    {
                        final long[] arrayD = append(hexagons[l], arrayC);
                        if(checkAll(arrayD)) continue;
                        for(int m = 0 ; m < heptagons.length ; m++)
                        {
                            final long[] arrayE = append(heptagons[m], arrayD);
                            if(checkAll(arrayE)) continue;
                            for(int n = 0 ; n < octagons.length ; n++)
                            {
                                final long[] arrayF = append(octagons[n], arrayE);
                                if(checkAll(arrayF))
                                {
                                    System.out.println(Arrays.toString(arrayF));
                                    System.out.println(Arrays.stream(arrayF).sum());
                                    break outerloop;
                                }
                            }
                        }
                    }
                }
            }
        }
        // System.out.println(checkAll(new long[] { 8128l, 2882l, 8281l }));
        final long END = System.nanoTime();
        System.out.println("" + (END - BEGIN) / 1000000.0d + "ms");
    }
}
