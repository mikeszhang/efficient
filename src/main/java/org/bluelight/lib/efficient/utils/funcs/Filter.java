package org.bluelight.lib.efficient.utils.funcs;

/**
 * decide if we should take something
 * @param <T> the type of thing we will decide
 * Created by mikes on 14-11-23.
 */
public interface Filter<T> {
    boolean accept(T t);
}
