package org.ikickass.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;

public class NavigableLinkedHashSet<E extends Comparable<? super E>> extends LinkedHashSet<E> implements NavigableSet<E>
{
    private NavigableLinkedHashSet<E>            PARENT;
    private final Set<NavigableLinkedHashSet<E>> CHILDREN;

    private static final Set<Thread> INFORM_THREADS = new HashSet<>();

    private enum InformMessages
    {
     ADD,
     REMOVE
    }

    private class LinkedHashComparator<T extends Comparable<? super T>> implements Comparator<T>
    {
        private final NavigableLinkedHashSet<T> PARENT;

        public LinkedHashComparator(NavigableLinkedHashSet<T> parent)
        {
            if(parent == null) throw new NullPointerException("Parent NavigableLinkedHashSet cannot be null.");
            PARENT = parent;
        }

        public int compare(final T one, final T two)
        {
            final List<T> elements = new ArrayList<>(PARENT);
            final int     oneIndex = elements.indexOf(one);
            final int     twoIndex = elements.indexOf(two);

            if(oneIndex < 0 ||
               twoIndex < 0) throw new IllegalArgumentException("Cannot order elements not belonging to the parent NavigableLinkedHashSet.  " + (oneIndex < 0 ? "one is not present.  " : "") + (twoIndex < 0 ? "two is not present." : ""));

            if(oneIndex < twoIndex) return -1;
            else if(oneIndex > twoIndex) return 1;
            else if(oneIndex == twoIndex) return 0;

            throw new IllegalStateException("This can never happen.");
        }
    }

    public NavigableLinkedHashSet()
    {
        super();
        CHILDREN = new HashSet<>();
        PARENT   = null;
    }

    private NavigableLinkedHashSet(final NavigableLinkedHashSet<E> parent)
    {
        super();
        CHILDREN = new HashSet<>();
        PARENT   = parent;
    }

    public NavigableLinkedHashSet(final int initialCapacity)
    {
        super(initialCapacity);
        CHILDREN = new HashSet<>();
        PARENT   = null;
    }

    public NavigableLinkedHashSet(final int initialCapacity, final float loadFactor)
    {
        super(initialCapacity, loadFactor);
        CHILDREN = new HashSet<>();
        PARENT   = null;
    }

    public NavigableLinkedHashSet(final Collection<? extends E> c)
    {
        super(c);
        CHILDREN = new HashSet<>();
        PARENT   = null;
    }

    private void register(final NavigableLinkedHashSet<E> child)
    {
        CHILDREN.add(child);
    }

    private void deregister(final NavigableLinkedHashSet<E> child)
    {
        CHILDREN.remove(child);
    }

    private void parentInform(final NavigableLinkedHashSet<E> parent, final InformMessages message, final E element)
    {
        if(parent != PARENT) throw new IllegalStateException("Can only parentInform() from parent.");

        for(final NavigableLinkedHashSet<E> child : CHILDREN)
        {
            child.parentInform(this, message, element);
        }
    }

    private synchronized void childInform(final NavigableLinkedHashSet<E> child, final InformMessages message, final E element)
    {
        if(!CHILDREN.contains(child)) throw new IllegalStateException("Can only childInform() from children.");

        if(PARENT == null)
        {
            while(!INFORM_THREADS.isEmpty())
            {
                try
                {
                    for(final Thread current : INFORM_THREADS)
                    {
                        current.join();
                        INFORM_THREADS.remove(current);
                    }
                } catch(final InterruptedException e)
                {
                }
            }

            for(final NavigableLinkedHashSet<E> currentChild : CHILDREN)
            {
                final Thread nextInform = new Thread(() -> currentChild.parentInform(this, message, element));
                INFORM_THREADS.add(nextInform);
                nextInform.start();
            }
        } else PARENT.parentInform(this, message, element);
    }

    @Override
    public Comparator<? super E> comparator()
    {
        return new LinkedHashComparator<E>(this);
    }

    @Override
    public E first()
    {
        for(final E current : this) return current;
        return null;
    }

    @Override
    public E last()
    {
        final List<E> elements = new ArrayList<>(this);
        return elements.get(this.size() - 1);
    }

    @Override
    public E lower(final E e)
    {
        final List<E> elements = new ArrayList<>(this);
        for(int i = 1 ; i < this.size() ; i++) if(elements.get(i).equals(e)) return elements.get(i - 1);
        return null;
    }

    @Override
    public E floor(final E e)
    {
        if(this.contains(e)) return e;
        else return lower(e);
    }

    @Override
    public E ceiling(final E e)
    {
        if(this.contains(e)) return e;
        else return higher(e);
    }

    @Override
    public E higher(final E e)
    {
        final List<E> elements = new ArrayList<>(this);
        for(int i = 0 ; i < this.size() - 1 ; i++) if(elements.get(i).equals(e)) return elements.get(i + 1);
        return null;
    }

    @Override
    public E pollFirst()
    {
        for(final E current : this)
        {
            this.remove(current);
            return current;
        }
        return null;
    }

    @Override
    public E pollLast()
    {
        if(this.size() == 0) return null;

        final List<E> elements    = new ArrayList<>(this);
        final E       lastElement = elements.get(this.size() - 1);
        this.remove(lastElement);
        return lastElement;
    }

    @Override
    public NavigableSet<E> descendingSet()
    {
        final NavigableLinkedHashSet<E> returnme = new NavigableLinkedHashSet<>();
        final List<E>                   elements = new ArrayList<>(this);
        for(final E currentElement : elements) returnme.add(currentElement);
        return returnme;
    }

    @Override
    public Iterator<E> descendingIterator()
    {
        List<E> mylist = new ArrayList<>(this);
        Collections.reverse(mylist);
        return mylist.iterator();
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SortedSet<E> headSet(E toElement)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SortedSet<E> tailSet(E fromElement)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
