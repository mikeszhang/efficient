package org.bluelight.lib.efficient.utils;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;
import org.bluelight.lib.efficient.constraint.NotNull;
import org.bluelight.lib.efficient.utils.funcs.ElementProcessor;
import org.apache.commons.collections4.Transformer;
import org.bluelight.lib.efficient.utils.funcs.KVProcessor;
import org.bluelight.lib.efficient.utils.funcs.Mapper;

import java.lang.reflect.Array;
import java.util.*;

/**
 * more utils than java collections, arrays and apache commons.
 * Created by mikes on 15-3-13.
 */
public class CollectionPlus {
    public static <T> Set<T> asSet(T... array){
        Set<T> set=new HashSet<T>();
        Collections.addAll(set, array);
        return set;
    }
    public static <K,V> Map<K,V> asMap(K key, V value){
        Map<K,V> map=new HashMap<K, V>();
        map.put(key, value);
        return map;
    }
    public static <K,V> Map<K,V> asMap(K key1, V value1, K key2, V value2){
        Map<K,V> map=asMap(key1,value1);
        map.put(key2,value2);
        return map;
    }
    public static <K,V> Map<K,V> asMap(K key1, V value1, K key2, V value2, K key3, V value3){
        Map<K,V> map=asMap(key1,value1,key2,value2);
        map.put(key3,value3);
        return map;
    }
    public static <T> T[] toArray(Class<T> clazz, Collection<? extends T> collection){
        if (collection==null){
            return null;
        }
        Object obj=Array.newInstance(clazz,collection.size());
        T[] array=(T[])obj;
        return collection.toArray(array);
    }
    public static <T> T[] add(Class<T> clazz, T[] src, T... elements){
        Object object=Array.newInstance(clazz,src.length+elements.length);
        for (int i=0;i<src.length;i++){
            Array.set(object,i,src[i]);
        }
        for (int i=0;i<elements.length;i++){
            Array.set(object,i+src.length,elements[i]);
        }
        return (T[])object;
    }
    public static <E,S> S foreach(Iterable<E> iterable, S initState, ElementProcessor<E,S> processor){
        if (iterable==null || processor==null){
            return initState;
        }
        S state = initState;
        try {
            Iterator<E> iterator = iterable.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                state = processor.process(i, iterator.next(), state);
                i++;
            }
        }
        catch (BreakException e){
            if (e.getCause()!=null){
                throw e;
            }
        }
        return state;
    }
    public static <E> void foreach(Iterable<E> iterable, ElementProcessor<E,Object> processor){
        CollectionPlus.foreach(iterable,null,processor);
    }
    public static <E,S> S foreach(E[] array, S initState, ElementProcessor<E,S> processor){
        if (array==null || processor==null){
            return initState;
        }
        S state=initState;
        try {
            for (int i = 0; i < array.length; i++) {
                state = processor.process(i, array[i], state);
            }
        }
        catch (BreakException e){
            if (e.getCause()!=null){
                throw e;
            }
        }
        return state;
    }
    public static <E> void foreach(E[] array, ElementProcessor<E,Object> processor){
        CollectionPlus.foreach(array,null,processor);
    }
    public static <K,V,S> S foreach(Map<K,V> map, S initState, KVProcessor<K,V,S> processor){
        if (map==null || processor==null){
            return initState;
        }
        S state=initState;
        try {
            for (Map.Entry<K,V> entry: map.entrySet()){
                state = processor.process(entry.getKey(),entry.getValue(),state);
            }
        }
        catch (BreakException e){
            if (e.getCause()!=null){
                throw e;
            }
        }
        return state;
    }
    public static <K,V> void foreach(Map<K,V> map, KVProcessor<K,V,Object> processor){
        CollectionPlus.foreach(map,null,processor);
    }
    public static void quitForeach(){
        throw new BreakException();
    }
    public static <I,O> Set<O> collect(Iterable<I> iterable, Transformer<? super I,? extends O> transformer){
        if (iterable==null){
            return null;
        }
        Set<O> set=new HashSet<O>();
        for (I el: iterable){
            set.add(transformer.transform(el));
        }
        return set;
    }
    public static <I> Set<I> asSet(Iterable<I> iterable){
        if (iterable==null){
            return null;
        }
        Set<I> set=new HashSet<I>();
        for (I el: iterable){
            set.add(el);
        }
        return set;
    }
    public static <K,E,V> BucketHashMap<K,E> map(Collection<V> collection, Mapper<K,E,V> mapper){
        BucketHashMap<K,E> bucketHashMap=new BucketHashMap<K, E>();
        for (V value: collection){
            bucketHashMap.append(mapper.map(value));
        }
        return bucketHashMap;
    }
    public static <K,V> BagMapAdaptor<K> count(Collection<V> collection, Mapper<K,Integer,V> mapper){
        Bag<K> bag=new HashBag<K>();
        for (V value: collection){
            Map<K, Integer> map=mapper.map(value);
            for (Map.Entry<K,Integer> entry: map.entrySet()){
                bag.add(entry.getKey(),entry.getValue());
            }
        }
        return new BagMapAdaptor<K>(bag);
    }
    public static <K,V> boolean putIfNotNull(@NotNull Map<K,V> map, K key, V value){
        if (key==null || value==null){
            return false;
        }
        map.put(key,value);
        return true;
    }
    public static <E> boolean addIfNotNull(@NotNull Collection<E> collection, E value){
        if (value==null){
            return false;
        }
        collection.add(value);
        return true;
    }
    public static <K,V> int trimNull(@NotNull Map<K,V> map){
        int before=map.size();
        Set<Map.Entry<K,V>> entrySet = map.entrySet();
        Iterator<Map.Entry<K,V>> iterator=entrySet.iterator();
        while (iterator.hasNext()){
            Map.Entry<K,V> entry=iterator.next();
            if (entry.getKey()==null || entry.getValue()==null){
                iterator.remove();
            }
        }
        return before-map.size();
    }
    public static <E> int trimNull(@NotNull Collection<E> collection){
        int before=collection.size();
        Iterator<E> iterator=collection.iterator();
        while (iterator.hasNext()){
            if (iterator.next()==null){
                iterator.remove();
            }
        }
        return before-collection.size();
    }
}
