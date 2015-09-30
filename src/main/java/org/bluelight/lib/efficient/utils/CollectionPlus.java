package org.bluelight.lib.efficient.utils;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.Predicate;
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

    public static <E> boolean addIf(@NotNull Collection<E> collection, E value, Predicate<E> predicate){
        if (predicate.evaluate(value)){
            collection.add(value);
            return true;
        }
        return false;
    }

    public static <E> boolean addIfNotNull(@NotNull Collection<E> collection, E value){
        if (value==null){
            return false;
        }
        collection.add(value);
        return true;
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
    public static <E> boolean isEmpty(E[] array){
        return array==null || array.length==0;
    }
    public static <E> boolean isNotEmpty(E[] array){
        return !isEmpty(array);
    }
}
