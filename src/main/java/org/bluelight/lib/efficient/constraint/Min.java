package org.bluelight.lib.efficient.constraint;

import java.lang.annotation.*;

/**
 * tell you this number value has min number.
 * Created by mikes on 15/8/6.
 */
@Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Min {
    double value();
}
