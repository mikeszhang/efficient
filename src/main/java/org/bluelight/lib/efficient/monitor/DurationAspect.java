package org.bluelight.lib.efficient.monitor;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * duration aspect.
 * Created by mikes on 15-4-27.
 */
@Aspect
@Component
public class DurationAspect {
    private static Log log= LogFactory.getLog(DurationAspect.class);
    @Around(value = "@annotation(org.bluelight.lib.efficient.monitor.Duration) && @annotation(duration)",
        argNames="joinPoint,duration")
    public Object recode(ProceedingJoinPoint joinPoint, Duration duration) throws Throwable {
        try {
            long start=System.currentTimeMillis();
            Object res=joinPoint.proceed();
            long end=System.currentTimeMillis();
            String info=joinPoint.getTarget().getClass().getSimpleName()+" "+joinPoint.getSignature().getName();
            System.out.println(info+" duration: "+(end-start));
            log.warn(info+" duration: "+(end-start));
            return res;
        }
        catch (Throwable e){
            throw e;
        }
    }
}
