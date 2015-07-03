package org.bluelight.lib.efficient.logic;

import java.util.Map;

/**
 * atomic proposition which always false.
 * Created by mikes on 15-2-15.
 */
public class FalseProposition implements Proposition {
    @Override
    public boolean value(Map<AtomicProposition, Boolean> assignmentMap, boolean defaultAssignment) {
        return false;
    }

    @Override
    public boolean containImplication() {
        return false;
    }

    @Override
    public boolean isNNF() {
        return true;
    }

    @Override
    public boolean isCNF() {
        return true;
    }

    @Override
    public boolean isDNF() {
        return true;
    }

    @Override
    public Proposition toNNF() {
        return this;
    }

    @Override
    public Proposition toNonImplication() {
        return this;
    }

    @Override
    public Proposition toCNF() {
        return this;
    }

    @Override
    public Proposition toDNF() {
        return this;
    }

    @Override
    public FalseProposition absorb() {
        return this;
    }

    @Override
    public String toString(){
        return "__FALSE__";
    }

    @Override
    public boolean isAtomic() {
        return true;
    }
    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }
    @Override
    public boolean equals(Object o){
        return o!=null && o instanceof FalseProposition;
    }
}
