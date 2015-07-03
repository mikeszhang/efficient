package org.bluelight.lib.efficient.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * disjoint set implementation based on hash.
 * @param <E> element type for subset.
 * Created by mikes on 15-3-17.
 */
public class HashDisjointSet<E> implements DisjointSet<E> {
    private Map<E,Set<E>> elementSetMap=new HashMap<E, Set<E>>();
    @Override
    public Set<E> find(E element) {
        return elementSetMap.get(element);
    }

    @Override
    public Set<E> union(Set<E> subSetA, Set<E> subSetB) {
        if (subSetA==null || subSetA.isEmpty() || subSetB==null || subSetB.isEmpty()) {
            return null;
        }
        Set<E> foundA=find(subSetA.iterator().next());
        Set<E> foundB=find(subSetB.iterator().next());
        if (!subSetA.equals(foundA) || !subSetB.equals(foundB)){
            return null;
        }
        if (foundA.size()>foundB.size()){
            foundA.addAll(foundB);
            for (E e: foundB){
                elementSetMap.put(e,foundA);
            }
            return foundA;
        }
        else {
            foundB.addAll(foundA);
            for (E e: foundA){
                elementSetMap.put(e,foundB);
            }
            return foundB;
        }
    }

    @Override
    public Set<E> makeSet(E element) {
        Set<E> already=this.find(element);
        if (already==null || already.isEmpty()){
            Set<E> set=new HashSet<E>();
            set.add(element);
            elementSetMap.put(element,set);
            return set;
        }
        else if (already.size()==1){
            return already;
        }
        else {
            already.remove(element);
            Set<E> set=new HashSet<E>();
            set.add(element);
            elementSetMap.put(element,set);
            return set;
        }
    }
    @Override
    public int hashCode(){
        return elementSetMap.hashCode()+this.getClass().hashCode();
    }
    @Override
    public boolean equals(Object obj){
        return obj!=null && obj instanceof HashDisjointSet && this.elementSetMap.equals(((HashDisjointSet) obj).elementSetMap);
    }
}
