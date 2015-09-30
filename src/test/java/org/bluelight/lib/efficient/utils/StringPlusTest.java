package org.bluelight.lib.efficient.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * string plus test.
 * Created by mikes on 15/9/30.
 */
public class StringPlusTest {
    @Test
    public void testJoin(){
        Assert.assertEquals("mikes-john", StringPlus.join("-", "mikes", "john"));
        Assert.assertEquals("mikes",StringPlus.join("-","mikes"));
        Assert.assertEquals("",StringPlus.join("-"));
        Assert.assertEquals("mikes john", StringPlus.blank("mikes", "john"));
        Assert.assertEquals("mikes,john", StringPlus.comma("mikes", "john"));
        Assert.assertEquals("mikes", StringPlus.blank("mikes"));
    }
}
