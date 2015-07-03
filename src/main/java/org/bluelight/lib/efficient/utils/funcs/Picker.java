package org.bluelight.lib.efficient.utils.funcs;

import org.apache.commons.lang3.tuple.Pair;

/**
 * pick from source to two parts
 * @param <T> pick from source type.
 * @param <L> left part type picked from source
 * @param <R> right part type picked from source
 * Created by mikes on 14-11-23.
 */
public interface Picker<T,L,R> {
    Pair<L,R> pick(T src);
}
