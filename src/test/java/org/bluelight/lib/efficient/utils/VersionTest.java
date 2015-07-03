package org.bluelight.lib.efficient.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mikes on 14-12-9.
 */
public class VersionTest {
    @Test
    public void testVersion() throws CloneNotSupportedException {
        String v=Version.covertString(40201);
        Assert.assertEquals("4.2.1",v);
        int vint=Version.convertInt("0.4.3");
        Assert.assertEquals(403,vint);
        v=Version.covertString(vint);
        Assert.assertEquals("0.4.3",v);
        Version version=new Version("4.9");
        Assert.assertTrue(version.compareTo(null)>0);
        Assert.assertEquals(version,version.clone());
        Assert.assertEquals(50566,Version.convertInt("5.5.566"));
    }
}
