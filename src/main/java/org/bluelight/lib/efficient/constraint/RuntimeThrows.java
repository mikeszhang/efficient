package org.bluelight.lib.efficient.constraint;

import java.lang.annotation.*;

/**
 * show method may throw what runtime exception.
 * Created by mikes on 15/8/18.
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface RuntimeThrows {
    Class<? extends RuntimeException>[] value();
}
