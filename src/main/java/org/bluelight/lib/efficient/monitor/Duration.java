package org.bluelight.lib.efficient.monitor;

import java.lang.annotation.*;

/**
 * annotation for duration in spring.
 * Created by mikes on 15-3-6.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Duration {
}
