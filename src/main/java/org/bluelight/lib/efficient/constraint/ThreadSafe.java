package org.bluelight.lib.efficient.constraint;

import java.lang.annotation.*;

/**
 * show all class object methods or specific method is thread safe.
 * Created by mikes on 15/8/18.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface ThreadSafe {
    boolean value() default true;
}
