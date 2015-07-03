package org.bluelight.lib.efficient.logic;

import java.util.*;

/**
 * utils for proposition.
 * Created by mikes on 15-2-15.
 */
public class PropositionUtils {
    static private final List<String> PRIORITY_LIST= Arrays.asList("~","&&","||","->");
    static private final String RESERVED="( )~&|->";
    static public Proposition parseProposition(String expression){
        Stack<String> operators=new Stack<String>();
        Stack<Proposition> operands=new Stack<Proposition>();
        int i=0;
        while (i<expression.length()){
            switch (expression.charAt(i)){
                case ' ':
                    i++;
                    break;
                case '(':
                    operators.push("(");
                    i++;
                    break;
                case ')':
                    if ("(".equals(operators.peek())){
                        operators.pop();
                        i++;
                    }
                    else {
                        operate(operators,operands);
                    }
                    break;
                case '~':
                    operators.push("~");
                    i++;
                    break;
                case '&':
                    if (needPush(operators,"&&")){
                        operators.push("&&");
                        i+=2;
                    }
                    else {
                        operate(operators, operands);
                    }
                    break;
                case '|':
                    if (needPush(operators,"||")){
                        operators.push("||");
                        i+=2;
                    }
                    else {
                        operate(operators,operands);
                    }
                    break;
                case '-':
                    if (needPush(operators,"->")){
                        operators.push("->");
                        i+=2;
                    }
                    else {
                        operate(operators,operands);
                    }
                    break;
                default:
                    String operand=eatOperand(expression,i);
                    operands.push(new AtomicProposition(operand));
                    i+=operand.length();
                    break;
            }
        }
        while (!operators.isEmpty()){
            operate(operators,operands);
        }
        return operands.pop();
    }
    static private boolean needPush(Stack<String> stack, String symbol){
        if (stack.isEmpty()){
            return true;
        }
        String top=stack.peek();
        int topIndex=PRIORITY_LIST.indexOf(top);
        return topIndex<0 || PRIORITY_LIST.indexOf(symbol)<=topIndex;
    }
    static private void operate(Stack<String> stack, Stack<Proposition> operands){
        String operator=stack.pop();
        Proposition p2=operands.pop();
        if ("~".equals(operator)){
            operands.push(new Negation(p2));
        }
        else {
            Proposition p1=operands.pop();
            if ("&&".equals(operator)){
                operands.push(new Conjunction(p1,p2));
            }
            else if ("||".equals(operator)){
                operands.push(new Disjunction(p1,p2));
            }
            else if ("->".equals(operator)){
                operands.push(new Implication(p1,p2));
            }
            else {
                operands.push(p1);
                operands.push(p2);
            }
        }
    }
    static private String eatOperand(String expression, int i){
        StringBuilder operand=new StringBuilder();
        while (i<expression.length()){
            char latter=expression.charAt(i);
            if (RESERVED.indexOf(latter)>=0){
                return operand.toString();
            }
            operand.append(latter);
            i++;
        }
        return operand.toString();
    }
}
