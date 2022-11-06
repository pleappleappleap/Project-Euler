package org.ikickass.number;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TriangularNumber extends InternalPolygonalNumber
{
    private static TriangularNumber        MY_INSTANCE;
    private final Map<Integer, BigInteger> MY_NUMBERS;

    private TriangularNumber()
    {
        MY_NUMBERS = new HashMap<>();
        MY_NUMBERS.put(1, BigInteger.ONE);
    }

    @Override
    protected Map<Integer, BigInteger> getNumbers()
    {
        synchronized(MY_NUMBERS)
        {
            return Collections.unmodifiableMap(MY_NUMBERS);
        }
    }

    @Override
    protected void calculate(int n)
    {
        if(!MY_NUMBERS.containsKey(n))
        {
            final BigInteger N       = BigInteger.valueOf(n);
            final BigInteger current = N.multiply(N.add(BigInteger.ONE)).divide(BigInteger.TWO);
            synchronized(MY_NUMBERS)
            {
                MY_NUMBERS.put(n, current);
            }

        }
    }

    public static TriangularNumber getInstance()
    {
        if(MY_INSTANCE == null) MY_INSTANCE = new TriangularNumber();
        return MY_INSTANCE;
    }

    public static BigInteger getTriangularNumber(final int i)
    {
        return getInstance().getNumber(i);
    }

    public static boolean isTriangular(final BigInteger n)
    {
        return getInstance().isNumber(n);
    }

    public static Stream<BigInteger> stream()
    {
        return getInstance().numberStream();
    }

    public static Stream<BigInteger> stream(final int count)
    {
        return getInstance().numberStream(count);
    }

    public static final void main(final String[] args)
    {
        TriangularNumber.stream(100).forEach(n -> System.out.println(n));
    }
}
