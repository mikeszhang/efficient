package org.bluelight.lib.efficient.utils.funcs;

import java.util.List;

/**
 * interface of reducer.
 * Created by mikes on 15/7/7.
 */
public interface Reducer<E,K,V> {
    E reduce(K key, List<V> bucket);
}
