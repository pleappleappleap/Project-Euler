package org.ikickass.util;

import java.util.AbstractMap;

public class Pair<K, V>
{
    public final K X;
    public final V Y;

    public Pair(final K key, final V value)
    {
        super();
        this.X   = key;
        this.Y = value;
    }

    public String toString()
    {
        return "" + X + " : " + Y;
    }

    public AbstractMap.SimpleEntry<K, V> toEntry()
    {
        return new AbstractMap.SimpleEntry<>(X, Y);
    }

    public Pair<V, K> invert()
    {
        return new Pair<>(Y, X);
    }

    public static <T, U> Pair<T, U> fromEntry(final AbstractMap.SimpleEntry<T, U> entry)
    {
        return new Pair<>(entry.getKey(), entry.getValue());
    }
    
    public int hashCode()
    {
        return X.hashCode() ^ (~(Y.hashCode()));
    }
    
    public boolean equals(final Object obj)
    {
        if(obj == null || obj.getClass() != Pair.class) return false;
        else
        {
            @SuppressWarnings("rawtypes") final Pair other = (Pair) obj;
            return X.equals(other.X) && Y.equals(other.Y); 
        }
    }
}
