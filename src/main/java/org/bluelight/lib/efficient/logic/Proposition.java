package org.bluelight.lib.efficient.logic;

import java.util.Map;

/**
 * notation for proposition.
 * Created by mikes on 15-2-15.
 */
public interface Proposition {
    String toString();
    boolean equals(Object proposition);
    int hashCode();
    boolean isAtomic();
    boolean value(Map<AtomicProposition,Boolean> assignmentMap, boolean defaultAssignment);
    boolean containImplication();
    boolean isNNF();
    boolean isCNF();
    boolean isDNF();
    Proposition toNNF();
    Proposition toNonImplication();
    Proposition toCNF();
    Proposition toDNF();
    Proposition absorb();
}
