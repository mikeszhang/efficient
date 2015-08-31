package org.bluelight.lib.efficient.http;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * test http service.
 * Created by mikes on 14-10-27.
 */
public class HttpServiceUtilsTest {
    @Test
    public void testGet() throws IOException{
        String res= HttpServiceUtils.getService().get("http://www.renren.com");
        Assert.assertTrue(res.length()>100);
    }
}
