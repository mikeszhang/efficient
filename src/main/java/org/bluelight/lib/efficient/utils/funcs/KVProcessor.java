package org.bluelight.lib.efficient.utils.funcs;

/**
 * key value processor for map iterate.
 * @param <K> key type.
 * @param <V> value type.
 * @param <S> state type.
 * Created by mikes on 15/7/10.
 */
public interface KVProcessor<K,V,S> {
    S process(K key, V value, S state);
}
