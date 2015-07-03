package org.bluelight.lib.efficient.logic;

import java.util.*;

/**
 * disjunction proposition.
 * Created by mikes on 15-2-15.
 */
public class Disjunction implements Proposition {
    private Set<Proposition> disjunctionSet=new HashSet<Proposition>();
    public Disjunction(Collection<? extends Proposition> propositions){
        for (Proposition p: propositions){
            if (p!=null){
                if (p instanceof Disjunction){
                    disjunctionSet.addAll(((Disjunction) p).disjunctionSet);
                }
                else {
                    disjunctionSet.add(p);
                }
            }
        }
        if (disjunctionSet.size()<2){
            throw new RuntimeException("disjunction item must have two item at least.");
        }
    }
    public Disjunction(Proposition p1,Proposition p2, Proposition... pn){
        if (p1!=null){
            if (p1 instanceof Disjunction){
                disjunctionSet.addAll(((Disjunction) p1).disjunctionSet);
            }
            else {
                disjunctionSet.add(p1);
            }
        }
        if (p2!=null){
            if (p2 instanceof Disjunction){
                disjunctionSet.addAll(((Disjunction) p2).disjunctionSet);
            }
            else {
                disjunctionSet.add(p2);
            }
        }
        for (Proposition p: pn){
            if (p!=null){
                if (p instanceof Disjunction){
                    disjunctionSet.addAll(((Disjunction) p).disjunctionSet);
                }
                else {
                    disjunctionSet.add(p);
                }
            }
        }
        if (disjunctionSet.size()<2){
            throw new RuntimeException("disjunction item must have two item at least.");
        }
    }
    @Override
    public boolean value(Map<AtomicProposition, Boolean> assignmentMap, boolean defaultAssignment) {
        for (Proposition p: disjunctionSet){
            if (p.value(assignmentMap,defaultAssignment)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containImplication() {
        for (Proposition p: disjunctionSet){
            if (p.containImplication()){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isNNF() {
        for (Proposition p: disjunctionSet){
            if (!p.isNNF()){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isCNF() {
        for (Proposition p: disjunctionSet){
            if (p instanceof Conjunction || !p.isNNF()){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isDNF() {
        for (Proposition p: disjunctionSet){
            if (!p.isNNF()){
                return false;
            }
            if (p instanceof Conjunction){
                for (Proposition subP: ((Conjunction) p).getConjunctionSet()){
                    if (subP instanceof Disjunction){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Disjunction toNNF() {
        if (this.isNNF()){
            return this;
        }
        Disjunction disjunction=this.toNonImplication();
        Set<Proposition> nnfSet=new HashSet<Proposition>();
        for (Proposition p: disjunction.disjunctionSet){
            nnfSet.add(p.toNNF());
        }
        return new Disjunction(nnfSet);
    }

    @Override
    public Disjunction toNonImplication() {
        if (!this.containImplication()){
            return this;
        }
        Set<Proposition> nonImpSet=new HashSet<Proposition>();
        for (Proposition p: disjunctionSet){
            nonImpSet.add(p.toNonImplication());
        }
        return new Disjunction(nonImpSet);
    }

    @Override
    public Proposition toCNF() {
        if (isCNF()){
            return this;
        }
        Disjunction nnf=this.toNNF();
        Set<Proposition> disSet=new HashSet<Proposition>();
        for (Proposition p: nnf.disjunctionSet){
            disSet.add(p.toCNF());
        }
        nnf=new Disjunction(disSet);
        Set<Proposition> conjSet=new HashSet<Proposition>();
        for (Proposition p: nnf.disjunctionSet){
            if (p instanceof Conjunction){
                conjSet=this.distributeToConjunction(conjSet, ((Conjunction) p).getConjunctionSet());
            }
            else {
                Set<Proposition> conjSetB=new HashSet<Proposition>();
                conjSetB.add(p);
                conjSet=this.distributeToConjunction(conjSet,conjSetB);
            }
        }
        if (conjSet.size()==1){
            return conjSet.iterator().next();
        }
        return new Conjunction(conjSet).absorb();
    }

    @Override
    public Proposition toDNF() {
        if (isDNF()){
            return this;
        }
        Disjunction nnf=this.toNNF();
        Set<Proposition> dnfSet=new HashSet<Proposition>();
        for (Proposition p: nnf.disjunctionSet){
            dnfSet.add(p.toDNF());
        }
        return new Disjunction(dnfSet).absorb();
    }

    @Override
    public Proposition absorb() {
        Set<Proposition> preparedSet=new HashSet<Proposition>();
        for (Proposition p: disjunctionSet){
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
        return new Disjunction(absorbedSet);
    }

    private Proposition absorbToOne(Proposition p1, Proposition p2){
        Set<Proposition> p1Set,p2Set;
        if (p1 instanceof Conjunction){
            p1Set=((Conjunction) p1).getConjunctionSet();
        }
        else {
            p1Set=new HashSet<Proposition>();
            p1Set.add(p1);
        }
        if (p2 instanceof Conjunction){
            p2Set=((Conjunction) p2).getConjunctionSet();
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

    private Set<Proposition> distributeToConjunction(Set<Proposition> conjSetA, Set<Proposition> conjSetB){
        if (conjSetA.isEmpty()) return conjSetB;
        if (conjSetB.isEmpty()) return conjSetA;
        Set<Proposition> conjSet=new HashSet<Proposition>();
        for (Proposition pA: conjSetA){
            for (Proposition pB: conjSetB){
                if (pA.equals(pB)){
                    conjSet.add(pA);
                }
                else {
                    conjSet.add(new Disjunction(pA, pB));
                }
            }
        }
        return conjSet;
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        for (Proposition p: disjunctionSet){
            if (p.isAtomic()){
                sb.append(p.toString());
            }
            else {
                sb.append('(').append(p.toString()).append(')');
            }
            sb.append(" || ");
        }
        return sb.substring(0,sb.length()-4);
    }

    @Override
    public boolean isAtomic() {
        return false;
    }

    @Override
    public int hashCode(){
        return this.disjunctionSet.hashCode()+"||".hashCode();
    }
    @Override
    public boolean equals(Object that){
        if (that==null || !(that instanceof Disjunction)){
            return false;
        }
        if (that==this){
            return true;
        }
        Disjunction proposition=(Disjunction)that;
        return this.disjunctionSet.equals(proposition.disjunctionSet);
    }
    public Set<Proposition> getDisjunctionSet(){
        return new HashSet<Proposition>(disjunctionSet);
    }
}
