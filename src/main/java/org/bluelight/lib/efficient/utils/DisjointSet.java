package org.bluelight.lib.efficient.utils;

import java.util.Set;

/**
 * interface for disjoint set.
 * @param <E> type of element for subset.
 * Created by mikes on 15-3-17.
 */
public interface DisjointSet<E> {
    Set<E> find(E element);
    Set<E> union(Set<E> subSetA, Set<E> subSetB);
    Set<E> makeSet(E element);
}
