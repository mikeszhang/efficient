package org.bluelight.lib.efficient.logic;

import java.util.Map;

/**
 * implication proposition.
 * Created by mikes on 15-2-15.
 */
public class Implication implements Proposition {
    private Proposition left;
    private Proposition right;
    public Implication(Proposition left, Proposition right){
        if (left==null || right==null){
            throw new NullPointerException("implication left and right should not be null.");
        }
        this.left=left;
        this.right=right;
    }
    @Override
    public boolean value(Map<AtomicProposition, Boolean> assignmentMap, boolean defaultAssignment) {
        return !left.value(assignmentMap,defaultAssignment) || right.value(assignmentMap,defaultAssignment);
    }

    @Override
    public boolean containImplication() {
        return true;
    }

    @Override
    public boolean isNNF() {
        return false;
    }

    @Override
    public boolean isCNF() {
        return false;
    }

    @Override
    public boolean isDNF() {
        return false;
    }

    @Override
    public Disjunction toNNF() {
        Disjunction nonImplication=this.toNonImplication();
        return nonImplication.toNNF();
    }

    @Override
    public Disjunction toNonImplication() {
        return new Disjunction(new Negation(left.toNonImplication()),right.toNonImplication());
    }

    @Override
    public Proposition toCNF() {
        return this.toNNF().toCNF();
    }

    @Override
    public Proposition toDNF() {
        return this.toNNF().toDNF();
    }

    @Override
    public Implication absorb() {
        return new Implication(left.absorb(),right.absorb());
    }

    @Override
    public String toString(){
        String leftStr, rightStr;
        if (this.left.isAtomic()){
            leftStr=this.left.toString();
        }
        else {
            leftStr='('+this.left.toString()+')';
        }
        if (this.right.isAtomic()){
            rightStr=this.right.toString();
        }
        else {
            rightStr='('+this.right.toString()+')';
        }
        return leftStr+"->"+rightStr;
    }

    @Override
    public boolean isAtomic() {
        return false;
    }

    @Override
    public int hashCode(){
        return this.left.hashCode()+this.right.hashCode()+"->".hashCode();
    }
    @Override
    public boolean equals(Object that){
        if (that==null || !(that instanceof Implication)){
            return false;
        }
        if (that==this){
            return true;
        }
        Implication proposition=(Implication)that;
        return this.left.equals(proposition.left) && this.right.equals(proposition.right);
    }
}
