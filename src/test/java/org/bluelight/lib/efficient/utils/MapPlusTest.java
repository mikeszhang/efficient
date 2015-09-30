package org.bluelight.lib.efficient.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * map plus test.
 * Created by mikes on 15/9/30.
 */
public class MapPlusTest {
    @Test
    public void testMapNull(){
        Map<String,String> map=new HashMap<String, String>();
        boolean a=MapPlus.putIfNotNull(map,"mikes","sda");
        boolean b=MapPlus.putIfNotNull(map,"ken",null);
        Assert.assertTrue(a);
        Assert.assertFalse(b);
        Assert.assertEquals(1,map.size());
        map.put("jack",null);
        map.put("coll",null);
        Assert.assertEquals(3,map.size());
        MapPlus.trimNull(map);
        Assert.assertEquals(1,map.size());
    }
}
