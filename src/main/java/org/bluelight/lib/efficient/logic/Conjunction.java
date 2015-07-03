package org.bluelight.lib.efficient.logic;

import java.util.*;

/**
 * conjunction proposition.
 * Created by mikes on 15-2-15.
 */
public class Conjunction implements Proposition {
    private Set<Proposition> conjunctionSet=new HashSet<Proposition>();
    public Conjunction(Collection<? extends Proposition> propositions){
        for (Proposition p: propositions){
            if (p!=null){
                if (p instanceof Conjunction){
                    conjunctionSet.addAll(((Conjunction) p).conjunctionSet);
                }
                else {
                    conjunctionSet.add(p);
                }
            }
        }
        if (conjunctionSet.size()<2){
            throw new RuntimeException("conjunction item must have two item at least.");
        }
    }
    public Conjunction(Proposition p1,Proposition p2, Proposition... pn){
        if (p1!=null){
            if (p1 instanceof Conjunction){
                conjunctionSet.addAll(((Conjunction) p1).conjunctionSet);
            }
            else {
                conjunctionSet.add(p1);
            }
        }
        if (p2!=null){
            if (p2 instanceof Conjunction){
                conjunctionSet.addAll(((Conjunction) p2).conjunctionSet);
            }
            else {
                conjunctionSet.add(p2);
            }
        }
        for (Proposition p: pn){
            if (p!=null){
                if (p instanceof Conjunction){
                    conjunctionSet.addAll(((Conjunction) p).conjunctionSet);
                }
                else {
                    conjunctionSet.add(p);
                }
            }
        }
        if (conjunctionSet.size()<2){
            throw new RuntimeException("conjunction item must have two item at least.");
        }
    }
    @Override
    public boolean value(Map<AtomicProposition, Boolean> assignmentMap, boolean defaultAssignment) {
        for (Proposition p: conjunctionSet){
            if (!p.value(assignmentMap,defaultAssignment)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean containImplication() {
        for (Proposition p: conjunctionSet){
            if (p.containImplication()){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isNNF() {
        for (Proposition p: conjunctionSet){
            if (!p.isNNF()){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isCNF() {
        for (Proposition p: conjunctionSet){
            if (!p.isNNF()){
                return false;
            }
            if (p instanceof Disjunction){
                for (Proposition subP: ((Disjunction) p).getDisjunctionSet()){
                    if (subP instanceof Conjunction){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean isDNF() {
        for (Proposition p: conjunctionSet){
            if (p instanceof Disjunction || !p.isNNF()){
                return false;
            }
        }
        return true;
    }

    @Override
    public Conjunction toNNF() {
        if (this.isNNF()){
            return this;
        }
        Conjunction conjunction=this.toNonImplication();
        Set<Proposition> nnfSet=new HashSet<Proposition>();
        for (Proposition p: conjunction.conjunctionSet){
            nnfSet.add(p.toNNF());
        }
        return new Conjunction(nnfSet);
    }

    @Override
    public Conjunction toNonImplication() {
        if (!this.containImplication()){
            return this;
        }
        Set<Proposition> nonImpSet=new HashSet<Proposition>();
        for (Proposition p: conjunctionSet){
            nonImpSet.add(p.toNonImplication());
        }
        return new Conjunction(nonImpSet);
    }

    @Override
    public Proposition toCNF() {
        if (isCNF()){
            return this;
        }
        Conjunction nnf=this.toNNF();
        Set<Proposition> cnfSet=new HashSet<Proposition>();
        for (Proposition p: nnf.conjunctionSet){
            cnfSet.add(p.toCNF());
        }
        return new Conjunction(cnfSet).absorb();
    }

    @Override
    public Proposition toDNF() {
        if (isDNF()){
            return this;
        }
        Conjunction nnf=this.toNNF();
        Set<Proposition> conjSet=new HashSet<Proposition>();
        for (Proposition p: nnf.conjunctionSet){
            conjSet.add(p.toDNF());
        }
        nnf=new Conjunction(conjSet);
        Set<Proposition> disjSet=new HashSet<Proposition>();
        for (Proposition p: nnf.conjunctionSet){
            if (p instanceof Disjunction){
                disjSet=this.distributeToDisjunction(disjSet, ((Disjunction) p).getDisjunctionSet());
            }
            else {
                Set<Proposition> disjSetB=new HashSet<Proposition>();
                disjSetB.add(p);
                disjSet=this.distributeToDisjunction(disjSet, disjSetB);
            }
        }
        if (disjSet.size()==1){
            return disjSet.iterator().next();
        }
        return new Disjunction(disjSet).absorb();
    }

    @Override
    public Proposition absorb() {
        Set<Proposition> preparedSet=new HashSet<Proposition>();
        for (Proposition p: conjunctionSet){
            preparedSet.add(p.absorb());
        }
        Set<Proposition> absorbedSet=new HashSet<Proposition>();
        for (Proposition p: preparedSet){
            Iterator<Proposition> iterator=absorbedSet.iterator();
            boolean absorbed=false;
            while (iterator.hasNext()){
                Proposition absorbedP=iterator.next();
                Proposition moreAbsorbed=this.absorbToOne(p,absorbedP);
                if (moreAbsorbed==p){
                    iterator.remove();
                }
                else if (moreAbsorbed==absorbedP){
                    absorbed=true;
                    break;
                }
            }
            if (!absorbed){
                absorbedSet.add(p);
            }
        }
        if (absorbedSet.size()==1)  return absorbedSet.iterator().next();
        return new Conjunction(absorbedSet);
    }

    private Proposition absorbToOne(Proposition p1, Proposition p2){
        Set<Proposition> p1Set,p2Set;
        if (p1 instanceof Disjunction){
            p1Set=((Disjunction) p1).getDisjunctionSet();
        }
        else {
            p1Set=new HashSet<Proposition>();
            p1Set.add(p1);
        }
        if (p2 instanceof Disjunction){
            p2Set=((Disjunction) p2).getDisjunctionSet();
        }
        else {
            p2Set=new HashSet<Proposition>();
            p2Set.add(p2);
        }
        if (p1Set.containsAll(p2Set)){
            return p2;
        }
        else if (p2Set.containsAll(p1Set)){
            return p1;
        }
        else {
            return null;
        }
    }
    private Set<Proposition> distributeToDisjunction(Set<Proposition> disjSetA, Set<Proposition> disjSetB){
        if (disjSetA.isEmpty()) return disjSetB;
        if (disjSetB.isEmpty()) return disjSetA;
        Set<Proposition> disjSet=new HashSet<Proposition>();
        for (Proposition pA: disjSetA){
            for (Proposition pB: disjSetB){
                if (pA.equals(pB)){
                    disjSet.add(pA);
                }
                else {
                    disjSet.add(new Conjunction(pA, pB));
                }
            }
        }
        return disjSet;
    }
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        for (Proposition p: conjunctionSet){
            if (p.isAtomic()){
                sb.append(p.toString());
            }
            else {
                sb.append('(').append(p.toString()).append(')');
            }
            sb.append(" && ");
        }
        return sb.substring(0,sb.length()-4);
    }

    @Override
    public boolean isAtomic() {
        return false;
    }
    @Override
    public int hashCode(){
        return this.conjunctionSet.hashCode()+"&&".hashCode();
    }
    @Override
    public boolean equals(Object that){
        if (that==null || !(that instanceof Conjunction)){
            return false;
        }
        if (that==this){
            return true;
        }
        Conjunction proposition=(Conjunction)that;
        return this.conjunctionSet.equals(proposition.conjunctionSet);
    }
    public Set<Proposition> getConjunctionSet(){
        return new HashSet<Proposition>(conjunctionSet);
    }
}
