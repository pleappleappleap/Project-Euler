package org.ikickass.number;

import java.math.BigInteger;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

abstract class InternalPolygonalNumber implements PolygonalNumber
{
    public final BigInteger getNumber(final int n)
    {
        final Map<Integer, BigInteger> myNumbers = getNumbers();
        synchronized(myNumbers)
        {
            if(myNumbers.containsKey(n)) return getNumbers().get(n);
            else
            {
                calculate(n);
                return getNumber(n);
            }
        }
    }

    public final boolean isNumber(final BigInteger n)
    {
        for(int i = 1 ;; i++)
        {
            if(getNumber(i).equals(n)) return true;
            else if(getNumber(i).compareTo(n) > 0) break;
        }
        return false;
    }

    protected final Stream<BigInteger> numberStream()
    {
        return IntStream.iterate(1, i -> i + 1).mapToObj(i -> getNumber(i));
    }

    protected final Stream<BigInteger> numberStream(final int count)
    {
        return IntStream.rangeClosed(1, count).mapToObj(i -> getNumber(i));
    }

    protected abstract Map<Integer, BigInteger> getNumbers();

    protected abstract void calculate(final int n);
}
