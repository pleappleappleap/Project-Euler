package org.ikickass.util;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class UndirectedGraph<T> extends Graph<T>
{
    @Override
    public boolean addEdge(final T a, final T b)
    {
        if(a == null || b == null)
        {
            throw new NullPointerException("Graph cannot contain null vertex.");
        }

        boolean flag = false;

        if(!MAP.containsKey(a))
        {
            flag = true;
            this.add(a);
        }

        if(!MAP.containsKey(b))
        {
            flag = true;
            this.add(b);
        }

        if(!MAP.get(a).contains(b))
        {
            flag = true;
            MAP.get(a).add(b);
        }

        if(!MAP.get(b).contains(a))
        {
            flag = true;
            MAP.get(b).add(a);
        }

        return flag;
    }

    @Override
    public int countEdges()
    {
        int count = 0;
        for(final Entry<T, Set<T>> currentEntry : MAP.entrySet())
        {
            count += currentEntry.getValue().size();
        }

        return count / 2;
    }

    @Override
    public boolean removeEdge(T a, T b)
    {
        if(a == null || b == null)
        {
            throw new NullPointerException("Graph cannot contain null vertex.");
        }

        if(!MAP.containsKey(a) || !MAP.containsKey(b)) throw new IllegalStateException("Missing vertex.");

        if(!MAP.get(a).contains(b) || !MAP.get(b).contains(a)) return false;
        
        MAP.get(a).remove(b);
        MAP.get(b).remove(a);
        return true;
    }

    @Override
    public List<T> dijkstra(T a, T b)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    public UndirectedGraph()
    {
        super();
    }

    public UndirectedGraph(final boolean sorted)
    {
        super(sorted);
    }
    
    public static void main(final String[] args)
    {
        final UndirectedGraph<Integer> graph = new UndirectedGraph<>(true);
        
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
        graph.addEdge(3, 6);
        graph.addEdge(4, 7);
        graph.addEdge(4, 3);
        System.out.println(graph.BFS(1));
        System.out.println(graph.DFS(1));
        System.out.println(graph.MAP);
        System.out.println(graph.paths(1));
    }
}
