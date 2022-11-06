package org.ikickass.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CountMap<T> implements Map<T, Long>
{
	private final Map<T, Long> INTERNAL_MAP;
	
	public CountMap()
	{
		INTERNAL_MAP = new LinkedHashMap<>();
	}
	
	@Override
	public final void clear()
	{
		throw new UnsupportedOperationException("Cannot clear a CountMap.");
	}
	
	@Override
	public final Long compute(final T key, final BiFunction<? super T, ? super Long, ? extends Long> remappingFunction)
	{
		throw new UnsupportedOperationException("Cannot put arbitrary values in a CountMap.");
	}
	
	@Override
	public final Long computeIfAbsent(final T key, final Function<? super T, ? extends Long> mappingFunction)
	{
		throw new UnsupportedOperationException("Cannot put arbitrary values in a CountMap.");
	}
	
	@Override
	public final Long computeIfPresent(final T key, final BiFunction<? super T, ? super Long, ? extends Long> remappingFunction)
	{
		throw new UnsupportedOperationException("Cannot put arbitrary values in a CountMap.");
	}
	
	@Override
	public boolean containsKey(final Object key)
	{
		return INTERNAL_MAP.containsKey(key);
	}
	
	@Override
	public boolean containsValue(final Object value)
	{
		return INTERNAL_MAP.containsValue(value);
	}
	
	public Long decrement(final T key)
	{
		return this.decrementBy(key, -1l);
	}
	
	public Long decrementBy(final T key, final Long value)
	{
		return this.incrementBy(key, -value);
	}
	
	@Override
	public final Set<Map.Entry<T, Long>> entrySet()
	{
		return Collections.unmodifiableMap(INTERNAL_MAP).entrySet();
	}
	
	// Default forEach() is fine.
	
	@Override
	public boolean equals(final Object o)
	{
		return INTERNAL_MAP.equals(o);
	}
	
	@Override
	public Long get(final Object key)
	{
		return INTERNAL_MAP.getOrDefault(key, 0l);
	}
	
	@Override
	public Long getOrDefault(final Object key, final Long defaultValue)
	{
		return INTERNAL_MAP.getOrDefault(key, defaultValue);
	}
	
	@Override
	public int hashCode()
	{
		return INTERNAL_MAP.hashCode();
	}
	
	public Long increment(final T key)
	{
		return this.incrementBy(key, 1l);
	}
	
	public Long incrementBy(final T key, final Long value)
	{
		if((key == null) || (value == null))
		{
			throw new NullPointerException("Keys and values in CountMap cannot be null");
		}
		
		INTERNAL_MAP.put(key, this.get(key) + value);
		return this.get(key);
	}
	
	@Override
	public boolean isEmpty()
	{
		return INTERNAL_MAP.isEmpty();
	}
	
	@Override
	public final Set<T> keySet()
	{
		return Collections.unmodifiableMap(INTERNAL_MAP).keySet();
	}
	
	@Override
	public final Long merge(final T key, final Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction)
	{
		throw new UnsupportedOperationException("Cannot put arbitrary values in a CountMap.");
	}
	
	@Override
	public final Long put(final T key, final Long value)
	{
		throw new UnsupportedOperationException("Cannot put arbitrary values in a CountMap.");
	}
	
	@Override
	public final void putAll(final Map<? extends T, ? extends Long> map)
	{
		throw new UnsupportedOperationException("Cannot put arbitrary values in a CountMap.");
	}
	
	@Override
	public final Long putIfAbsent(final T key, final Long value)
	{
		throw new UnsupportedOperationException("Cannot put arbitrary values in a CountMap.");
	}
	
	@Override
	public final Long remove(final Object key)
	{
		throw new UnsupportedOperationException("Cannot remove arbitrary values in a CountMap.");
	}
	
	@Override
	public final boolean remove(final Object key, final Object value)
	{
		throw new UnsupportedOperationException("Cannot remove arbitrary values in a CountMap.");
	}
	
	@Override
	public final Long replace(final T key, final Long value)
	{
		throw new UnsupportedOperationException("Cannot put arbitrary values in a CountMap.");
	}
	
	@Override
	public final boolean replace(final T key, final Long oldValue, final Long newValue)
	{
		throw new UnsupportedOperationException("Cannot put arbitrary values in a CountMap.");
	}
	
	@Override
	public final void replaceAll(final BiFunction<? super T, ? super Long, ? extends Long> function)
	{
		throw new UnsupportedOperationException("Cannot put arbitrary values in a CountMap.");
	}
	
	@Override
	public int size()
	{
		return INTERNAL_MAP.size();
	}
	
	@Override
	public final Collection<Long> values()
	{
		return Collections.unmodifiableMap(INTERNAL_MAP).values();
	}
	
	public Long total()
	{
	    return this.values().stream().mapToLong(l -> l).sum();
	}
	
	public String toString()
	{
	    return INTERNAL_MAP.toString();
	}
}
