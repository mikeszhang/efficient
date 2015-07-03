package org.bluelight.lib.efficient.monitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * assert in log for construct no bug software.
 * Created by mikes on 15/7/25.
 */
public class Assert {
    private static Log myLog = LogFactory.getLog(Assert.class);
    private static final String ASSERTION_FAIL_INFO="Assertion failed, must be some bug! ";
    /**
     * condition must be true, or the program will have bug.
     * use it for bug free if you don't understand pre/post condition and invariant well.
     * */
    static public void sure(boolean condition, Object msg, Log log){
        if (!condition){
            if (log!=null) {
                log.error(ASSERTION_FAIL_INFO + msg);
            }
            else {
                Assert.myLog.error(ASSERTION_FAIL_INFO + msg);
            }
        }
    }
    /**
     * before complex loop or business dealing, some condition must hold.
     * condition must be true, or the program will have bug.
     * */
    static public void preCondition(boolean condition, Object msg, Log log){
        Assert.sure(condition, "Pre-condition failed! "+msg, log);
    }
    /**
     * after complex loop or business dealing, some condition must hold.
     * condition must be true, or the program will have bug.
     * */
    static public void postCondition(boolean condition, Object msg, Log log){
        Assert.sure(condition, "Post-condition Failed! "+msg, log);
    }
    /**
     * in each complex loop or business dealing, the some condition must hold.
     * condition must be true, or the program will have bug and object state will be wrong.
     * */
    static public void invariant(boolean condition, Object msg, Log log){
        Assert.sure(condition, "Invariant Failed! "+msg, log);
    }
    /**
     * when you think a branch of code should never be reached, put the method there.
     * */
    static public void unreachable(Object msg, Log log){
        Assert.sure(false, "Unreachable code was reached! "+msg, log);
    }
}
