package org.bluelight.lib.efficient.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * test object plus.
 * Created by mikes on 15/8/30.
 */
public class ObjectPlusTest {
    @Test
    public void testRequireCheck(){
        Assert.assertTrue(ObjectPlus.allNonNull("adas",23,56.2));
        Assert.assertTrue(ObjectPlus.allNull(null, null, null));
        Assert.assertTrue(ObjectPlus.containNullAndNonNull(null,"sda"));
        Assert.assertFalse(ObjectPlus.containNullAndNonNull(null,null));
        Assert.assertFalse(ObjectPlus.containNullAndNonNull(23,"dssad"));
        ObjectPlus.requireAllNonNull("dada","ss",45);
        ObjectPlus.requireNotAllNull(null,23,"sds");
        int state=3;
        state++;
        ObjectPlus.argumentRequire(state==4,"only allow 4 in state.");
    }
    @Test
    public void testJoin(){
        Assert.assertEquals("mikes-john",ObjectPlus.join("-","mikes","john"));
        Assert.assertEquals("mikes",ObjectPlus.join("-","mikes"));
        Assert.assertEquals("",ObjectPlus.join("-"));
    }
}
