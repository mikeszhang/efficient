package org.bluelight.lib.efficient.constraint;

import java.lang.annotation.*;

/**
 * show class object is immutable.
 * immutable class method => thread safe and reentrant.
 * Created by mikes on 15/8/18.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Immutable {
    boolean value() default true;
}
