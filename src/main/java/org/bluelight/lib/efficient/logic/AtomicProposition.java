package org.bluelight.lib.efficient.logic;

import java.util.Map;

/**
 * atomic proposition.
 * Created by mikes on 15-2-15.
 */
public class AtomicProposition implements Proposition {
    private Object symbol;
    public AtomicProposition(Object symbol){
        if (symbol==null){
            throw new NullPointerException("symbol should not be null.");
        }
        this.symbol=symbol;
    }
    @Override
    public boolean value(Map<AtomicProposition,Boolean> assignmentMap, boolean defaultAssignment) {
        if (assignmentMap!=null){
            Boolean assignment=assignmentMap.get(this);
            if (assignment!=null){
                return assignment;
            }
        }
        return defaultAssignment;
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
    public AtomicProposition absorb() {
        return this;
    }

    @Override
    public String toString(){
        return this.symbol.toString();
    }

    @Override
    public boolean isAtomic() {
        return true;
    }

    @Override
    public boolean equals(Object that){
        if (that==null || !(that instanceof AtomicProposition)){
            return false;
        }
        AtomicProposition atomicProposition=(AtomicProposition)that;
        return this.symbol.equals(atomicProposition.symbol);
    }
    @Override
    public int hashCode(){
        return this.symbol.hashCode();
    }
}
