package org.bluelight.lib.efficient.utils.funcs;

/**
 * process element in collection or array.
 * @param <E> element type to be processed.
 * @param <S> current and after process state type.
 * Created by mikes on 15-3-16.
 */
public interface ElementProcessor<E,S> {
    S process(int index, E element, S currentState);
}
