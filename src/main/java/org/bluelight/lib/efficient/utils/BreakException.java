package org.bluelight.lib.efficient.utils;

/**
 * runtime break exception for loop.
 * Created by mikes on 15-3-20.
 */
public class BreakException extends RuntimeException {
    public BreakException(Throwable e){
        super(e);
    }
    public BreakException(String msg){
        super(msg);
    }
    public BreakException(){
        super();
    }
}
