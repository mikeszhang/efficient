package org.bluelight.lib.efficient.constraint;

import java.lang.annotation.*;

/**
 * show the class instance create or method call is large overhead, do not do it frequently.
 * Created by mikes on 15/8/18.
 */
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Expensive {
    String value() default "";
}
