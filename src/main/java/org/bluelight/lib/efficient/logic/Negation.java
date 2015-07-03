package org.bluelight.lib.efficient.logic;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * negation proposition.
 * Created by mikes on 15-2-15.
 */
public class Negation implements Proposition {
    private Proposition proposition;
    public Negation(Proposition proposition){
        if (proposition==null){
            throw new NullPointerException("proposition should not be null.");
        }
        this.proposition=proposition;
    }
    @Override
    public boolean value(Map<AtomicProposition, Boolean> assignmentMap, boolean defaultAssignment) {
        return !proposition.value(assignmentMap,defaultAssignment);
    }

    @Override
    public boolean containImplication() {
        return proposition.containImplication();
    }

    @Override
    public boolean isNNF() {
        return proposition.isAtomic();
    }

    @Override
    public boolean isCNF() {
        return proposition.isAtomic();
    }

    @Override
    public boolean isDNF() {
        return proposition.isAtomic();
    }

    @Override
    public Proposition toNNF() {
        if (this.isNNF()){
            return this;
        }
        if (!proposition.isAtomic()){
            Proposition nonImplication=proposition.toNonImplication();
            if (nonImplication instanceof Negation){
                return ((Negation) nonImplication).proposition.toNNF();
            }
            else if (nonImplication instanceof Conjunction){
                Set<Proposition> propositionSet=((Conjunction) nonImplication).getConjunctionSet();
                Set<Proposition> negationSet=new HashSet<Proposition>();
                for (Proposition p: propositionSet){
                    negationSet.add(new Negation(p));
                }
                return new Disjunction(negationSet).toNNF();
            }
            else if (nonImplication instanceof Disjunction){
                Set<Proposition> propositionSet=((Disjunction) nonImplication).getDisjunctionSet();
                Set<Proposition> negationSet=new HashSet<Proposition>();
                for (Proposition p: propositionSet){
                    negationSet.add(new Negation(p));
                }
                return new Conjunction(negationSet).toNNF();
            }
            else {
                throw new RuntimeException("implication free must be a bug.");
            }
        }
        return this;
    }

    @Override
    public Negation toNonImplication() {
        if (!this.containImplication()){
            return this;
        }
        return new Negation(proposition.toNonImplication());
    }

    @Override
    public Proposition toCNF() {
        if (isCNF()){
            return this;
        }
        Proposition nnf=this.toNNF();
        if (nnf instanceof Negation){
            return nnf;
        }
        return nnf.toCNF();
    }

    @Override
    public Proposition toDNF() {
        if (isDNF()){
            return this;
        }
        Proposition nnf=this.toNNF();
        if (nnf instanceof Negation){
            return nnf;
        }
        return nnf.toDNF();
    }

    @Override
    public Negation absorb() {
        return new Negation(proposition.absorb());
    }

    @Override
    public String toString(){
        if (proposition.isAtomic()) {
            return "~"+proposition.toString();
        }
        return "~("+proposition.toString()+")";
    }

    @Override
    public boolean isAtomic() {
        return false;
    }
    @Override
    public int hashCode(){
        return this.proposition.hashCode()+"~".hashCode();
    }
    @Override
    public boolean equals(Object o) {
        return !(o == null || !(o instanceof Negation)) && (o == this || proposition.equals(((Negation) o).proposition));
    }
}
