package org.bluelight.lib.efficient.utils;

import com.google.common.primitives.Ints;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

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
        ObjectPlus.requireNotEmpty(Arrays.asList(12), "is not empty");
        ObjectPlus.requireNotBlank(" s", "not blank");
        ObjectPlus.requireNotEmpty(MapPlus.asMap(12,22), "map is not empty");
        ObjectPlus.requireNotEmpty(new String[1], "array is 1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequireFailed(){
        ObjectPlus.requireNotEmpty(new String[0], "is empty");
    }
}
