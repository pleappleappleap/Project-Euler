import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Problem2
{
    public static class Matrix<T extends Number>
    {
        private final T[][] DATA;

        @SuppressWarnings("unchecked")
        public Matrix(final T ONE, final T TWO, final T THREE, final T FOUR)
        {
            final T[] COLUMNS = (T[]) Array.newInstance(ONE.getClass(), 0);
            DATA       = (T[][]) Array.newInstance(COLUMNS.getClass(), 2);
            DATA[0]    = (T[]) Array.newInstance(ONE.getClass(), 2);
            DATA[1]    = (T[]) Array.newInstance(ONE.getClass(), 2);
            DATA[0][0] = ONE;
            DATA[0][1] = TWO;
            DATA[1][0] = THREE;
            DATA[1][1] = FOUR;
        }

        public Matrix(final Matrix<T> copyMe)
        {
            this(copyMe.DATA[0][0], copyMe.DATA[0][1], copyMe.DATA[1][0], copyMe.DATA[1][1]);
        }

        public static Matrix<BigInteger> base()
        {
            return new Matrix<>(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO);
        }

        public void applyScalar(final T scalar, final BiFunction<T, T, T> applyMe)
        {
            DATA[0][0] = applyMe.apply(DATA[0][0], scalar);
            DATA[0][1] = applyMe.apply(DATA[0][1], scalar);
            DATA[1][0] = applyMe.apply(DATA[1][0], scalar);
            DATA[1][1] = applyMe.apply(DATA[1][1], scalar);
        }

        public void applyMatrix(final Matrix<T> matrix, final BiFunction<T, T, T> applyMe)
        {
            DATA[0][0] = applyMe.apply(DATA[0][0], matrix.DATA[0][0]);
            DATA[0][1] = applyMe.apply(DATA[0][1], matrix.DATA[0][1]);
            DATA[1][0] = applyMe.apply(DATA[1][0], matrix.DATA[1][0]);
            DATA[1][1] = applyMe.apply(DATA[1][1], matrix.DATA[1][1]);
        }

        public Matrix<T> multiply(final Matrix<T> matrix,
                                  final BiFunction<T, T, T> multiplyFunction,
                                  final BiFunction<T, T, T> addFunction)
        {
            T ONE   = addFunction.apply(multiplyFunction.apply(DATA[0][0], matrix.DATA[0][0]),
                                        multiplyFunction.apply(DATA[1][0], matrix.DATA[0][1]));
            T TWO   = addFunction.apply(multiplyFunction.apply(DATA[0][0], matrix.DATA[0][1]),
                                        multiplyFunction.apply(DATA[0][1], matrix.DATA[1][1]));
            T THREE = addFunction.apply(multiplyFunction.apply(DATA[1][0], matrix.DATA[0][0]),
                                        multiplyFunction.apply(DATA[1][1], matrix.DATA[1][0]));
            T FOUR  = addFunction.apply(multiplyFunction.apply(DATA[1][0], matrix.DATA[0][1]),
                                        multiplyFunction.apply(DATA[1][1], matrix.DATA[1][1]));

            return new Matrix<>(ONE, TWO, THREE, FOUR);
        }

        public Matrix<T> power(final int exponent,
                               final BiFunction<T, T, T> multiplyFunction,
                               final BiFunction<T, T, T> addFunction)
        {
            int       power  = 1;
            Matrix<T> result = this;
            while(power < exponent)
            {
                result = result.multiply(this, multiplyFunction, addFunction);
                power++;
            }

            return result;
        }

        public T get(final int index)
        {
            switch (index)
            {
                case 0:
                    return DATA[0][0];
                case 1:
                    return DATA[0][1];
                case 2:
                    return DATA[1][0];
                case 3:
                    return DATA[1][1];
                default:
                    return null;
            }
        }

        public T get()
        {
            return get(1);
        }

        public String toString()
        {
            return DATA[0][0] + ":" + DATA[0][1] + ":" + DATA[1][0] + ":" + DATA[1][1];
        }
    }

    private static final int                         RECURSIVE_LIMIT = 17277;
    private static final int                         LIMIT           = RECURSIVE_LIMIT * 2;
    private static final MathContext                 ALMOST_INFINITE = new MathContext(100000, RoundingMode.HALF_UP);
    private static final BigDecimal                  SQRT_5          = new BigDecimal("5").sqrt(ALMOST_INFINITE);
    private static final BigDecimal                  PHI             =
            (BigDecimal.ONE.add(SQRT_5)).divide(new BigDecimal("2"), ALMOST_INFINITE);
    private static final BigDecimal                  PSI             =
            (BigDecimal.ONE.subtract(SQRT_5)).divide(new BigDecimal("2"), ALMOST_INFINITE);
    private static final Map<BigInteger, BigInteger> CACHE           = new HashMap<>();

    public static Stream<BigInteger> fibonacciNumbersByIteration()
    {
        return Stream.iterate(new BigInteger[] { BigInteger.ONE, BigInteger.ZERO }, A ->
        {
            final BigInteger RESULT = A[1].equals(BigInteger.ZERO) ? BigInteger.ONE : A[0].add(A[1]);

            A[1] = A[0];
            A[0] = RESULT;
            return A;
        }).map(A -> A[0]);
    }

    public static Stream<BigInteger> fibonacciNumbersByRecursion()
    {
        return IntStream.iterate(0, i -> i + 1).mapToObj(i -> fibonacciNumberByRecursion(i));
    }

    public static Stream<BigInteger> fibonacciNumbersByFormula()
    {
        return IntStream.iterate(0, i -> i + 1).mapToObj(i -> fibonacciNumberByFormula(i));
    }

    public static Stream<BigInteger> fibonacciNumbersByMatrixExponentiation()
    {
        return IntStream.iterate(0, i -> i + 1).mapToObj(i -> fibonacciNumberByMatrixExponentiation(i));
    }

    public static Stream<BigInteger> fibonacciNumbersByRecursiveFormula()
    {
        return IntStream.iterate(0, i -> i + 1).mapToObj(i -> fibonacciNumberByRecursiveFormula(i));
    }

    public static Stream<BigInteger> fibonacciNumbersByNaiveRecursion()
    {
        return IntStream.iterate(0, i -> i + 1).mapToObj(i -> fibonacciNumberByNaiveRecursion(i));
    }

    public static BigInteger fibonacciNumberByIteration(final int n)
    {
        final Iterator<BigInteger> iterator = fibonacciNumbersByIteration().iterator();
        int                        i        = 0;
        while(iterator.hasNext())
        {
            final BigInteger result = iterator.next();
            if(++i > n) return result;
        }
        throw new AssertionError("We should never reach here.");
    }

    public static BigInteger fibonacciNumberByRecursion(final int n)
    {
        final BigInteger N = new BigInteger(Integer.toString(n));
        if(CACHE.containsKey(N)) return CACHE.get(N);
        if(n > RECURSIVE_LIMIT) return BigInteger.ZERO;

        switch (n)
        {
            case 0:
            case 1:
                CACHE.put(N, BigInteger.ONE);
                return BigInteger.ONE;
            default:
                final BigInteger RESULT = fibonacciNumberByRecursion(n - 2).add(fibonacciNumberByRecursion(n - 1));
                CACHE.put(N, RESULT);
                return RESULT;
        }
    }

    public static BigInteger fibonacciNumberByMatrixExponentiation(int n)
    {
        final BigInteger N = new BigInteger(Integer.toString(n));
        if(CACHE.containsKey(N)) return CACHE.get(N);
        switch (n)
        {
            case 0:
            case 1:
                CACHE.put(N, BigInteger.ONE);
                return BigInteger.ONE;
            default:
                final BigInteger RESULT = Matrix.base().power(n, BigInteger::multiply, BigInteger::add).get();
                CACHE.put(N, RESULT);
                return RESULT;
        }
    }

    public static BigInteger fibonacciNumberByRecursiveFormula(int n)
    {
        final BigInteger N = new BigInteger(Integer.toString(n));
        if(CACHE.containsKey(N)) return CACHE.get(N);
        switch (n)
        {
            case 0:
            case 1:
                CACHE.put(N, BigInteger.ONE);
                return BigInteger.ONE;
            case 2:
                CACHE.put(N, BigInteger.ONE);
                return BigInteger.ONE;
            default:
                final int half = n / 2;
                final int onemore = half + 1;
                final BigInteger a = fibonacciNumberByRecursiveFormula(onemore);
                final BigInteger b = fibonacciNumberByRecursiveFormula(half);
                System.err.println("k = " + half + ", k + 1 = " + onemore + ", n = " + N + ", a = " + a + ", b = " + b);
                BigInteger result = null;
                if(n % 2 == 1)
                {
                    result = b.multiply(BigInteger.TWO.multiply(a).subtract(b));
                } else
                {
                    result = a.multiply(a).add(b.multiply(b));
                }
                CACHE.put(N, result);
                return result;
        }
    }

    public static BigInteger fibonacciNumberByNaiveRecursion(final int n)
    {
        switch (n)
        {
            case 0:
            case 1:
                return BigInteger.ONE;
            default:
                return fibonacciNumberByNaiveRecursion(n - 1).add(fibonacciNumberByNaiveRecursion(n - 2));
        }
    }

    public static BigInteger fibonacciNumberByFormula(final int n)
    {
        // Binet's Formula:
        // (phi^n-psi^n)/sqrt(5)
        final MathContext MY_CONTEXT = new MathContext(n / 2 + 2, RoundingMode.HALF_UP);
        return PHI.pow(n + 1, MY_CONTEXT).subtract(PSI.pow(n + 1, MY_CONTEXT)).divide(SQRT_5, MY_CONTEXT).toBigInteger();
    }

    public static class FibonacciResult
    {
        final String[]     names;
        final BigInteger[] results;
        final long[]       times;

        public FibonacciResult(final int size)
        {
            names   = new String[size];
            results = new BigInteger[size];
            times   = new long[size];
        }

        public FibonacciResult(final String[] newNames, final long[] newTimes, final BigInteger[] newResults)
        {
            names   = newNames;
            results = newResults;
            times   = newTimes;
        }

        public void set(final int index, final String name, final long time, final BigInteger result)
        {
            if(index < 0 || index >= times.length) throw new ArrayIndexOutOfBoundsException();

            names[index]   = name;
            results[index] = result;
            times[index]   = time;
        }

        public String toString()
        {
            final int namesLength   = Arrays.stream(names).mapToInt(s -> s.length()).max().getAsInt();
            final int resultsLength = Arrays.stream(results).mapToInt(bi -> bi.toString().length()).max().getAsInt();
            final int timesLength   = Arrays.stream(times).mapToInt(l -> Long.toString(l).length()).max().getAsInt();

            return IntStream.range(0, results.length)
                            .mapToObj(i -> String.format("%" + namesLength +
                                                         "s: %" +
                                                         timesLength +
                                                         "d ns %" +
                                                         resultsLength +
                                                         "d\n",
                                                         names[i],
                                                         times[i],
                                                         results[i]))
                            .collect(StringBuilder::new, (sb, s) -> sb.append(s), (sb1, sb2) -> sb1.append(sb2))
                            .toString();
        }
    }

    public static FibonacciResult timeFibonacci(final int limit)
    {
        final String[]     NAMES   =
                { "START", "Iteration", "Formula", "Recursion", "Matrix Exponentiation", "Recursive Formula", "Naive Recursion" };
        final long[]       TIMES   = new long[7];
        final BigInteger[] RESULTS = new BigInteger[7];

        CACHE.clear();
        RESULTS[0] = BigInteger.ZERO;
        TIMES[0]   = System.nanoTime();

        CACHE.clear();
        RESULTS[1] = fibonacciNumberByIteration(limit);
        TIMES[1]   = System.nanoTime();

        CACHE.clear();
        RESULTS[2] = fibonacciNumberByFormula(limit);
        TIMES[2]   = System.nanoTime();

        CACHE.clear();
        RESULTS[3] = fibonacciNumberByRecursion(limit);
        TIMES[3]   = System.nanoTime();

        CACHE.clear();
        RESULTS[4] = fibonacciNumberByMatrixExponentiation(limit);
        TIMES[4]   = System.nanoTime();

        CACHE.clear();
        RESULTS[5] = fibonacciNumberByRecursiveFormula(limit);
        TIMES[5]   = System.nanoTime();

        CACHE.clear();
        RESULTS[6] = fibonacciNumberByNaiveRecursion(limit);
        TIMES[6]   = System.nanoTime();

        for(int i = TIMES.length - 1 ; i >= 0 ; i--)
        {
            TIMES[i] = i > 0 ? TIMES[i] - TIMES[i - 1] : 0;
        }

        return new FibonacciResult(NAMES, TIMES, RESULTS);
    }

    public static void main(final String[] args)
    {
        
    }
}
