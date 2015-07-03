package org.bluelight.lib.efficient.utils;

import org.bluelight.lib.efficient.utils.funcs.ElementProcessor;

import java.util.Collection;
import java.util.Iterator;

/**
 * abstract trie.
 * Created by mikes on 15-3-27.
 * @param <E> element type for abstract trie.
 */
public abstract class AbstractTrie<E extends CharSequence> implements Trie<E> {
    @Override
    public int size() {
        int i=0;
        for (E e : this) {
            i++;
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return this.size()==0;
    }

    @Override
    public Object[] toArray() {
        final Object[] array=new Object[size()];
        CollectionPlus.foreach(this, new ElementProcessor<E, Object>() {
            @Override
            public Object process(int index, E element, Object currentState) {
                array[index]=element;
                return currentState;
            }
        });
        return array;
    }

    @Override
    public <T> T[] toArray(final T[] ts) {
        final int size=size();
        if (ts.length<size){
            return (T[])toArray();
        }
        CollectionPlus.foreach(this, new ElementProcessor<E, Object>() {
            @Override
            public Object process(int index, E element, Object currentState) {
                ts[index]=(T)element;
                return currentState;
            }
        });
        CollectionPlus.foreach(ts, new ElementProcessor<T, Object>() {
            @Override
            public Object process(int index, T element, Object currentState) {
                if (index>=size){
                    ts[index]=null;
                }
                return currentState;
            }
        });
        return ts;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object el: collection){
            if (!contains(el)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean changed=false;
        for (E object: collection){
            changed = changed || add(object);
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean changed=false;
        for (Object object: collection){
            changed = changed || remove(object);
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        int beforeSize=size();
        Iterator<E> iterator=this.iterator();
        while (iterator.hasNext()){
            E el=iterator.next();
            if (!collection.contains(el)){
                iterator.remove();
            }
        }
        return size()!=beforeSize;
    }

    @Override
    public void clear() {
        Iterator<E> iterator=iterator();
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
    }
}
