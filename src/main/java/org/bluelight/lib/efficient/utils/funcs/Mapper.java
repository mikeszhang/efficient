package org.bluelight.lib.efficient.utils.funcs;

import java.util.Map;

/**
 * interface for mapper.
 * Created by mikes on 15/7/7.
 */
public interface Mapper<K,V,E> {
    Map<K,V> map(E element);
}
