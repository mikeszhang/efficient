package org.bluelight.lib.efficient.constraint;

import java.lang.annotation.*;

/**
 * show the method is mainly for a remote call (http, rpc or socket).
 * Created by mikes on 15/8/18.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Remote {
    String msg() default "";
    int timeout() default 0;
}
