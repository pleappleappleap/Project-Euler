package org.ikickass.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public abstract class Graph<T> implements Collection<T>
{
    protected final Map<T, Set<T>> MAP;

    @Override
    public int size()
    {
        return MAP.size();
    }

    @Override
    public boolean isEmpty()
    { return MAP.isEmpty(); }

    @Override
    public boolean contains(Object o)
    {
        if(o == null) throw new NullPointerException("Graph cannot contain null vertex.");
        if(MAP.isEmpty()) return false;

        for(final T current : MAP.keySet())
        {
            if(!current.getClass().isInstance(o)) throw new ClassCastException("Wrong type for contains: " + o);
            break;
        }

        return MAP.containsKey(o);
    }

    @Override
    public Iterator<T> iterator()
    {
        return MAP.keySet().iterator();
    }

    @Override
    public Object[] toArray()
    {
        return MAP.keySet().toArray();
    }

    @Override
    public <A> A[] toArray(A[] a)
    {
        return MAP.keySet().toArray(a);
    }

    @Override
    public boolean add(T e)
    {
        if(e == null) throw new NullPointerException("Cannot have null vertex in a graph.");
        if(MAP.containsKey(e)) return true;

        MAP.put(e, new LinkedHashSet<>());

        return true;
    }

    @Override
    public boolean remove(Object o)
    {
        if(o == null) throw new NullPointerException("Cannot have a null vertex in a graph.");

        if(!MAP.containsKey(o)) return false;

        MAP.remove(o);

        for(final Entry<T, Set<T>> currentEntry : MAP.entrySet())
        {
            final Set<T> currentSet = currentEntry.getValue();
            if(currentSet.contains(o)) currentSet.remove(o);
        }

        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return MAP.keySet().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        if(c == null) throw new NullPointerException("Cannot addAll(null).");

        for(final T current : c)
        {
            this.add(current);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        if(c == null) throw new NullPointerException("Cannot removeAll(null).");
        if(MAP.isEmpty() || c.isEmpty()) return false;

        boolean flag = false;

        for(final Object current : c)
        {
            for(final T current2 : MAP.keySet())
            {
                if(!current2.getClass().isInstance(current))
                {
                    throw new ClassCastException("Collection for removeAll has wrong type.");
                }
                break;
            }
            break;
        }

        for(final Object current : c)
        {
            @SuppressWarnings("unchecked")
            final T typedCurrent = (T) current;
            if(MAP.containsKey(typedCurrent))
            {
                flag = true;
                this.remove(typedCurrent);
            }
        }

        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        if(c == null) throw new NullPointerException("Cannot retainAll(null).");
        if(MAP.isEmpty() || c.isEmpty()) return false;

        boolean flag = false;

        for(final T current : MAP.keySet())
        {
            if(!c.contains(current))
            {
                flag = true;
                this.remove(current);
            }
        }

        return flag;
    }

    @Override
    public void clear()
    {
        MAP.clear();
    }

    public Graph()
    {
        this(false);
    }

    public Graph(final boolean sorted)
    {
        if(sorted) MAP = new TreeMap<>();
        else MAP = new LinkedHashMap<>();
    }

    public abstract boolean addEdge(final T a, final T b);

    public abstract boolean removeEdge(final T a, final T b);

    public abstract int countEdges();

    public List<T> BFS(final T root)
    {
        return BFS(root, new HashSet<>());
    }

    private List<T> BFS(final T root, final Set<T> visited)
    {
        final List<T> returnme = new ArrayList<>();

        if(visited.contains(root)) return returnme;

        final Set<T> edges = MAP.get(root);

        visited.add(root);
        returnme.add(root);
        returnme.addAll(edges);

        for(final T current : edges)
        {
            final List<T> nextList = BFS(current, visited);
            for(final T next : nextList)
            {
                if(!returnme.contains(next)) returnme.add(next);
            }
        }

        return returnme;
    }

    public List<T> DFS(final T root)
    {
        return DFS(root, new HashSet<>());
    }

    private List<T> DFS(final T root, final Set<T> visited)
    {
        final List<T> returnme = new ArrayList<>();

        if(visited.contains(root)) return returnme;

        final Set<T> edges = MAP.get(root);

        visited.add(root);
        returnme.add(root);
        for(final T current : edges)
        {
            final List<T> nextList = DFS(current, visited);
            for(final T next : nextList)
            {
                if(!returnme.contains(next)) returnme.add(next);
            }
        }
        return returnme;
    }

    public abstract List<T> dijkstra(final T a, final T b);

    public List<List<T>> paths(final T root)
    {
        if(root == null) throw new NullPointerException("Graph cannot contain null vertex.");
        if(!MAP.containsKey(root)) throw new IllegalArgumentException("Root for paths doesn't exist: " + root);
        final List<T> staging = new ArrayList<>();
        staging.add(root);
        return filter(paths(staging));
    }

    private List<List<T>> paths(final List<T> previous)
    {
        final T             root     = previous.get(previous.size() - 1);
        final Set<T>        edges    = MAP.get(root);
        final List<List<T>> returnme = new ArrayList<>();

        for(final T next : edges)
        {
            if(previous.contains(next)) continue;
            final List<T> staging = new ArrayList<>();
            staging.addAll(previous);
            staging.add(next);
            try
            {
                returnme.addAll(paths(staging));
            } catch(final OutOfMemoryError e)
            {
                System.err.println("length = " + returnme.size());
            }
        }

        if(returnme.isEmpty()) returnme.add(previous);

        return returnme;
    }

    private List<List<T>> filter(final List<List<T>> filterMe)
    {
        final Map<Pair<T, T>, List<T>> output = new LinkedHashMap<>();

        for(final List<T> currentPath : filterMe)
        {
            final Pair<T, T> current = new Pair<>(currentPath.get(0), currentPath.get(currentPath.size() - 1));
            if(!output.containsKey(current) || output.get(current).size() > currentPath.size()) output.put(current, currentPath);
        }

        System.err.println(output);

        return new ArrayList<>(output.values());
    }

    public Set<T> nodes()
    {
        return Collections.unmodifiableSet(MAP.keySet());
    }

    public List<T> findCycle(final T root, final int length)
    {
        final List<T> returnme = new ArrayList<>();
        returnme.add(root);
        returnme.addAll(MAP.get(root)
                           .stream()
                           .map(next -> findPaths(next, root, length - 1))
                           .filter(path -> path.size() == length)
                           .findFirst()
                           .get()
                           .stream()
                           .findFirst()
                           .get());
        return returnme;
    }

    public Set<List<T>> findPaths(final T start, final T end)
    {
        return findPaths(start, end, MAP.size());
    }

    public Set<List<T>> findPaths(final T start, final T end, final int maxLength)
    {
        return findPaths(start, end, maxLength, Set.of(start));
    }

    private Set<List<T>> findPaths(final T start, final T end, final int maxLength, final Set<T> visited)
    {
        final Set<List<T>> returnme = new LinkedHashSet<>();

        if(!visited.contains(start)) throw new IllegalStateException("We haven't been to our start point.");
        else if(visited.contains(end)) return returnme;
        else
        {
            if(containsEdge(start, end))
            {
                final List<T> addme = new ArrayList<>();
                addme.add(start);
                addme.add(end);
                returnme.add(addme);
            }

            for(final T current : MAP.get(start))
            {
                if(visited.contains(current)) continue;
                final Set<T> newVisited = new LinkedHashSet<>();
                newVisited.add(current);
                newVisited.addAll(visited);
                final Set<List<T>> nextPaths = findPaths(current, end, maxLength - 1, newVisited);
                nextPaths.stream().forEach(list -> list.add(0, start));
                returnme.addAll(nextPaths.stream().filter(list -> list.size() <= maxLength).toList());
            }
        }

        if(returnme.size() > 0) System.err.println("" + start + ":" + end + ":" + returnme);
        return returnme;
    }

    public boolean containsEdge(final T a, final T b)
    {
        if(a == null | b == null) throw new NullPointerException("Graph cannot contain null vertex.");
        if(!MAP.containsKey(a) || !MAP.containsKey(b)) return false;
        return MAP.get(a).contains(b);
    }

    public String toString()
    {
        return MAP.toString();
    }
}
