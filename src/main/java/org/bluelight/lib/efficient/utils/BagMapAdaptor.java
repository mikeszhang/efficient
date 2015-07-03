package org.bluelight.lib.efficient.utils;

import org.apache.commons.collections4.Bag;

import java.util.*;

/**
 * adaptor to Map using apache Bag.
 * this class only supports read methods. if you want to modify, please getBag and call Bag modify method.
 * Created by mikes on 15/8/4.
 */
public class BagMapAdaptor<K> extends AbstractMap<K,Integer>{
    private Bag<K> bag;
    public BagMapAdaptor(Bag<K> bag){
        if (bag==null){
            throw new NullPointerException("BagMapAdaptor not for null bag.");
        }
        this.bag=bag;
    }
    public Bag<K> getBag(){
        return this.bag;
    }
    @Override
    public Set<Entry<K, Integer>> entrySet() {
        Set<Entry<K, Integer>> set=new HashSet<Entry<K, Integer>>();
        for (K key : bag) {
            set.add(new SimpleEntry<K, Integer>(key, bag.getCount(key)));
        }
        return set;
    }
    @Override
    public void clear(){
        throw new UnsupportedOperationException("please call getBag and clear");
    }
    @Override
    public boolean containsKey(Object key){
        return this.bag.contains(key);
    }
    @Override
    public boolean equals(Object o) {
        return o instanceof BagMapAdaptor && this.bag.equals(((BagMapAdaptor) o).bag);
    }
    @Override
    public Integer get(Object key){
        if (!this.containsKey(key)){
            return null;
        }
        return this.bag.getCount(key);
    }
    @Override
    public int hashCode(){
        return this.bag.hashCode();
    }
    @Override
    public boolean isEmpty(){
        return this.bag.isEmpty();
    }
    @Override
    public Set<K> keySet(){
        return this.bag.uniqueSet();
    }
    @Override
    public int size(){
        return this.bag.uniqueSet().size();
    }
    @Override
    public void putAll(Map<? extends K, ? extends Integer> map){
        throw new UnsupportedOperationException("please call getBag and add");
    }
    @Override
    public Integer remove(Object key){
        throw new UnsupportedOperationException("please call getBag and remove");
    }
}
