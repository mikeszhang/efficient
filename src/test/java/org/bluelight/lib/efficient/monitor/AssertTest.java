package org.bluelight.lib.efficient.monitor;

import org.junit.Test;

/**
 * assert test.
 * Created by mikes on 15/7/27.
 */
public class AssertTest {
    @Test
    public void testSure(){
        Assert.sure(false,"java rum time bug",null);
        Assert.unreachable("I am here.",null);
    }
}
