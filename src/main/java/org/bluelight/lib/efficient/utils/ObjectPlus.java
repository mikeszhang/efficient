package org.bluelight.lib.efficient.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.bluelight.lib.efficient.constraint.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * more object utils than Objects and MoreObjects.
 * Created by mikes on 15/8/30.
 */
public class ObjectPlus {
    public static <T> void requireNotAllNull(T... objs){
        if (allNull(objs)) {
            throw new NullPointerException("all args are null");
        }
    }
    public static <T> void requireAllNonNull(T... objs){
        for (int i=0; i<objs.length; i++){
            if (objs[i]==null){
                throw new NullPointerException("find null in args of index "+i);
            }
        }
    }
    public static <T> boolean allNull(T... objs){
        for (T obj : objs) {
            if (obj != null) {
                return false;
            }
        }
        return true;
    }
    public static <T> boolean allNonNull(T... objs){
        for (T obj : objs) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }
    public static <T> boolean containNullAndNonNull(T... objs){
        return !allNull(objs) && !allNonNull(objs);
    }

    public static void argumentRequire(boolean condition, String msg){
        if (!condition){
            throw new IllegalArgumentException(msg);
        }
    }
    public static void requireNotBlank(CharSequence str, String msg){
        if (StringUtils.isBlank(str)){
            throw new IllegalArgumentException(msg);
        }
    }
    public static void requireNotEmpty(CharSequence str, String msg){
        if (StringUtils.isEmpty(str)){
            throw new IllegalArgumentException(msg);
        }
    }
    public static void requireNotEmpty(Collection<?> collection, String msg){
        if (CollectionUtils.isEmpty(collection)){
            throw new IllegalArgumentException(msg);
        }
    }
    public static void requireNotEmpty(Map<?,?> map, String msg){
        if (MapUtils.isEmpty(map)){
            throw new IllegalArgumentException(msg);
        }
    }
    public static <E> void requireNotEmpty(E[] array, String msg){
        if (CollectionPlus.isEmpty(array)){
            throw new IllegalArgumentException(msg);
        }
    }
}
