package org.bluelight.lib.efficient.monitor;

import java.lang.annotation.*;

/**
 * flow control.
 * Created by mikes on 15-4-29.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Flow {
    String name() default "";
    int qps();
}
