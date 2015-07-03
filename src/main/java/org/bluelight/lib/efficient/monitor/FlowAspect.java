package org.bluelight.lib.efficient.monitor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * flow control aspect.
 * Created by mikes on 15-4-29.
 */
@Aspect
@Component
public class FlowAspect {
    private ConcurrentMap<String,Current> currentMap=new ConcurrentHashMap<String, Current>();

    @Around(value = "@annotation(org.bluelight.lib.efficient.monitor.Flow) && @annotation(flow)",
            argNames="joinPoint,flow")
    public Object recode(ProceedingJoinPoint joinPoint, Flow flow) throws Throwable {
        String name=flow.name();
        if (StringUtils.isEmpty(name)){
            name=joinPoint.getSignature().getName();
        }
        if (bellowLimit(name, flow.qps())) {
            return joinPoint.proceed();
        }
        return null;
    }
    private boolean bellowLimit(String name, int qps){
        Current current=this.currentMap.putIfAbsent(name, new Current(0,0));
        synchronized (this.currentMap.get(name)) {
            long currentSeconds=System.currentTimeMillis()/1000;
            if (currentSeconds == current.currentTime) {
                current.currentCount++;
                return current.currentCount <= qps;
            }
            current.currentTime = currentSeconds;
            current.currentCount = 1;
            return true;
        }
    }
    /**
     * inner class for wrap current time and flow count.
     * */
    private class Current{
        public long currentTime=0;
        public int currentCount=0;
        public Current (long currentTime, int currentCount){
            this.currentTime=currentTime;
            this.currentCount=currentCount;
        }
    }
}
