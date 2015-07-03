package org.bluelight.lib.efficient.utils;

import java.util.Collection;

/**
 * interface for trie with element of type string like.
 * Created by mikes on 15-3-26.
 * @param <T> trie node element type.
 */
public interface Trie<T extends CharSequence> extends Collection<T> {
    <E extends CharSequence> boolean hasPrefix(E prefix);
    <E extends CharSequence> Iterable<T> prefixAs(E prefix);
}
