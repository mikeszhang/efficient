package org.bluelight.lib.efficient.constraint;

import java.lang.annotation.*;

/**
 * show important things which cannot find a proper annotation to indicate.
 * Created by mikes on 15/8/18.
 */
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.PACKAGE, ElementType.PARAMETER,ElementType.FIELD,ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Attention {
    String value();
}
