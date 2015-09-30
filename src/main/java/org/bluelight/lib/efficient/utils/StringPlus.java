package org.bluelight.lib.efficient.utils;

import org.bluelight.lib.efficient.constraint.NotNull;

/**
 * more string utils.
 * Created by mikes on 15/9/30.
 */
public class StringPlus {
    public static String join(@NotNull String separator, Object... objs){
        StringBuilder sb=new StringBuilder();
        for (Object obj: objs){
            sb.append(obj).append(separator);
        }
        if (objs.length>0) {
            sb.deleteCharAt(sb.length() - separator.length());
        }
        return sb.toString();
    }

    public static String blank(Object... objs){
        return join(" ",objs);
    }

    public static String comma(Object... objs){
        return join(",",objs);
    }
}
