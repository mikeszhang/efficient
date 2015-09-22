package org.bluelight.lib.efficient.utils;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * test parse utils.
 * Created by mikes on 15/7/6.
 */
public class ParseUtilsTest {
    @Test
    public void test(){
        int a=ParseUtils.parseInt("sda",0);
        Assert.assertEquals(0,a);
        List<Integer> intList=ParseUtils.batchParseInt(Arrays.asList("s","11","21"),false);
        Assert.assertArrayEquals(Arrays.asList(11,21).toArray(),intList.toArray());
        Pair<Double,Double> latlng=ParseUtils.parsePosition("123.221,233.32", ",", 0, 0);
        Assert.assertEquals(Pair.of(123.221, 233.32), latlng);
        Object data=ParseUtils.parseScalar("17.6");
        Assert.assertTrue(data instanceof Float);
        Assert.assertEquals(17.6f,data);
        Assert.assertNull(ParseUtils.parseInt("asd",null));
    }
}
