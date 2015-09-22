package org.bluelight.lib.efficient.utils;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * provide useful String number parsing.
 * Created by mikes on 14-11-21.
 */
public class ParseUtils {
    public static List<Integer> batchParseInt(Collection collection, boolean throwable){
        return batchParseInt(collection.toArray(),throwable);
    }
    public static List<Integer> batchParseInt(Object[] array, boolean throwable){
        List<Integer> list=new ArrayList<Integer>();
        for (Object el: array){
            if (el==null && throwable){
                throw new NumberFormatException("null for array: "+ Arrays.toString(array));
            }
            else if (el!=null) {
                Integer res=Ints.tryParse(el.toString());
                if (res==null && throwable){
                    throw new NumberFormatException("parse error for: "+ Arrays.toString(array));
                }
                if (res!=null){
                    list.add(res);
                }
            }
        }
        return list;
    }
    public static List<Long> batchParseLong(Object[] array, boolean throwable){
        List<Long> list=new ArrayList<Long>();
        for (Object el: array){
            if (el==null && throwable){
                throw new NumberFormatException("null for array: "+ Arrays.toString(array));
            }
            else if (el!=null) {
                Long res=Longs.tryParse(el.toString());
                if (res==null && throwable){
                    throw new NumberFormatException("parse error for: "+ Arrays.toString(array));
                }
                if (res!=null){
                    list.add(res);
                }
            }
        }
        return list;
    }
    public static List<Boolean> batchParseBoolean(Object[] array){
        List<Boolean> list=new ArrayList<Boolean>();
        for (Object el: array){
            Boolean res = el!=null && Boolean.valueOf(el.toString());
            list.add(res);
        }
        return list;
    }
    public static List<Double> batchParseDouble(Object[] array, boolean throwable){
        List<Double> list=new ArrayList<Double>();
        for (Object el: array){
            if (el==null && throwable){
                throw new NumberFormatException("null for array: "+ Arrays.toString(array));
            }
            else if (el!=null) {
                Double res=Doubles.tryParse(el.toString());
                if (res==null && throwable){
                    throw new NumberFormatException("parse error for: "+ Arrays.toString(array));
                }
                if (res!=null){
                    list.add(res);
                }
            }
        }
        return list;
    }
    public static List<Float> batchParseFloat(Object[] array, boolean throwable){
        List<Float> list=new ArrayList<Float>();
        for (Object el: array){
            if (el==null && throwable){
                throw new NumberFormatException("null for array: "+ Arrays.toString(array));
            }
            else if (el!=null) {
                Float res=Floats.tryParse(el.toString());
                if (res==null && throwable){
                    throw new NumberFormatException("parse error for: "+ Arrays.toString(array));
                }
                if (res!=null){
                    list.add(res);
                }
            }
        }
        return list;
    }
    public static Integer parseInt(String value, Integer defaultValue){
        if (value == null){
            return defaultValue;
        }
        Integer res=Ints.tryParse(value);
        return res==null?defaultValue:res;
    }
    public static Boolean parseBoolean(String value, Boolean defaultValue){
        if (value == null){
            return defaultValue;
        }
        Boolean res=tryParseBoolean(value);
        return res==null?defaultValue:res;
    }
    public static Float parseFloat(String value, Float defaultValue){
        if (value == null){
            return defaultValue;
        }
        Float res= Floats.tryParse(value);
        return res==null?defaultValue:res;
    }
    public static Double parseDouble(String value, Double defaultValue){
        if (value == null){
            return defaultValue;
        }
        Double res= Doubles.tryParse(value);
        return res==null?defaultValue:res;
    }
    public static Long parseLong(String value, Long defaultValue){
        if (value == null){
            return defaultValue;
        }
        Long res= Longs.tryParse(value);
        return res==null?defaultValue:res;
    }
    public static Pair<Double,Double> parsePosition(String value, String separator, double defaultLat, double defaultLng){
        String[] arr=value.split(separator);
        double lat = defaultLat;
        double lng = defaultLng;
        if (arr.length>0) {
            lat = ParseUtils.parseDouble(arr[0],defaultLat);
        }
        if (arr.length>1) {
            lng = ParseUtils.parseDouble(arr[1],defaultLng);
        }
        return Pair.of(lat,lng);
    }
    public static Boolean tryParseBoolean(String value){
        if (value==null){
            return null;
        }
        if (value.equalsIgnoreCase("true")){
            return true;
        }
        else if (value.equalsIgnoreCase("false")){
            return false;
        }
        return null;
    }
    /**
     * try int, long, float, double, boolean in order.
     * true/false => boolean.
     * return original string value if parse failed.
     * */
    public static Object parseScalar(String value){
        if (value == null){
            return value;
        }
        Object res=Ints.tryParse(value);
        if (res!=null){
            return res;
        }
        res=Longs.tryParse(value);
        if (res!=null){
            return res;
        }
        res=Floats.tryParse(value);
        if (res!=null){
            return res;
        }
        res=Doubles.tryParse(value);
        if (res!=null){
            return res;
        }
        res=tryParseBoolean(value);
        if (res!=null){
            return res;
        }
        return value;
    }
}
