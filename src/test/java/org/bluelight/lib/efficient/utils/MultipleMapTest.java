package org.bluelight.lib.efficient.utils;

import junit.framework.Assert;
import org.junit.Test;

/**
 * test multiple map.
 * Created by mikes on 15/7/2.
 */
public class MultipleMapTest {
    @Test
    public void testAll(){
        MultipleMap<String,Integer,String> multipleMap=new MultipleMap<String, Integer, String>();
        multipleMap.put("mikes",1,"tank");
        Assert.assertTrue(multipleMap.containsKey("mikes",1));
        Assert.assertFalse(multipleMap.containsKey("mikes",2));
        Assert.assertFalse(multipleMap.containsKey("jack",2));
        Assert.assertEquals("tank",multipleMap.get("mikes",1));
    }
}
