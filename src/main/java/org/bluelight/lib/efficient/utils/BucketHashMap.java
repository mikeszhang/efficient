package org.bluelight.lib.efficient.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.bluelight.lib.efficient.utils.funcs.Picker;
import org.apache.commons.lang3.tuple.Pair;
import org.bluelight.lib.efficient.utils.funcs.Reducer;

import java.util.*;

/**
 * a map whose value is a list
 * @param <K> map key type
 * @param <E> value type of list in map value
 * Created by mikes on 14-11-21.
 */
public class BucketHashMap<K,E> extends HashMap<K,List<E>> {
    public BucketHashMap(){
        super();
    }
    public <T> BucketHashMap(Collection<T> collection, Picker<T,K,E> picker){
        for (T t: collection){
            Pair<K,E> pair=picker.pick(t);
            if (pair!=null) {
                this.append(pair.getLeft(), pair.getRight());
            }
        }
    }
    public void append(K key, E element){
        if (!this.containsKey(key)){
            this.put(key, new ArrayList<E>());
        }
        this.get(key).add(element);
    }
    public void append(Map<K,E> map){
        for (Map.Entry<K,E> entry: map.entrySet()){
            this.append(entry.getKey(),entry.getValue());
        }
    }
    public int sizeOf(K key){
        List<E> bucket=this.get(key);
        if (bucket==null){
            return 0;
        }
        return bucket.size();
    }
    public int sizeOfAll(){
        int size=0;
        Set<K> set=this.keySet();
        for (K key: set){
            size+=this.sizeOf(key);
        }
        return size;
    }
    public boolean isEmpty(K key){
        return this.sizeOf(key)==0;
    }
    public List<Map.Entry<K,E>> flat(){
        List<Map.Entry<K,E>> all=new ArrayList<Map.Entry<K,E>>();
        Set<K> set=this.keySet();
        for (K key: set){
            List<E> bucket=this.get(key);
            for (E e: bucket){
                all.add(new SimpleEntry<K,E>(key,e));
            }
        }
        return all;
    }

    @Override
    public List<E> put(K key, List<E> bucket){
        if (bucket==null){
            bucket=new ArrayList<E>();
        }
        return super.put(key,bucket);
    }
    public E getFistValue(K key){
        List<E> bucket=this.get(key);
        if (CollectionUtils.isEmpty(bucket)){
            return null;
        }
        return bucket.get(0);
    }
    public <V> Map<K,V> reduce(Reducer<V,K,E> reducer){
        Map<K,V> result=new HashMap<K, V>();
        for (Map.Entry<K,List<E>> entry: this.entrySet()){
            result.put(entry.getKey(), reducer.reduce(entry.getKey(), entry.getValue()));
        }
        return result;
    }
}
