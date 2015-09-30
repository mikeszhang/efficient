package org.bluelight.lib.efficient.utils;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.tuple.Pair;
import org.bluelight.lib.efficient.constraint.NotNull;
import org.bluelight.lib.efficient.utils.funcs.Mapper;

import java.util.*;

/**
 * more map utils.
 * Created by mikes on 15/9/30.
 */
public class MapPlus {
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
    public static <K,E,V> BucketHashMap<K,E> map(Collection<V> collection, Mapper<K,E,V> mapper){
        BucketHashMap<K,E> bucketHashMap=new BucketHashMap<K, E>();
        for (V value: collection){
            bucketHashMap.append(mapper.map(value));
        }
        return bucketHashMap;
    }

    public static <K,V> boolean putIf(@NotNull Map<K,V> map, K key, V value, Predicate<Pair<K,V>> predicate){
        if (predicate.evaluate(Pair.of(key,value))){
            map.put(key,value);
            return true;
        }
        return false;
    }

    public static <K,V> boolean putIfNotNull(@NotNull Map<K,V> map, K key, V value){
        if (key==null || value==null){
            return false;
        }
        map.put(key,value);
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
}
