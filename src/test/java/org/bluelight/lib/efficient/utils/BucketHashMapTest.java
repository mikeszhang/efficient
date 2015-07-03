package org.bluelight.lib.efficient.utils;

import org.bluelight.lib.efficient.utils.funcs.Mapper;
import org.bluelight.lib.efficient.utils.funcs.Reducer;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * test bucket map and some collection plus.
 * Created by mikes on 14-11-22.
 */
public class BucketHashMapTest {
    @Test
    public void testBucket(){
        BucketHashMap<String,Integer> bucketHashMap=new BucketHashMap<String, Integer>();
        bucketHashMap.append("mikes",13);
        bucketHashMap.append("mikes",14);
        Assert.assertEquals(2,bucketHashMap.sizeOf("mikes"));
        Assert.assertEquals(0,bucketHashMap.sizeOf("john"));
        bucketHashMap.append("shone",7);
        Assert.assertEquals(3,bucketHashMap.sizeOfAll());
        Assert.assertEquals(3,bucketHashMap.flat().size());
    }
    @Test
    public void testMapReduce() throws IOException {
        List<String> words= Arrays.asList("the","world","is","a","fuck","world");
        BucketHashMap<String,Integer> bucketHashMap=CollectionPlus.map(words, new Mapper<String, Integer, String>() {
            @Override
            public Map<String, Integer> map(String element) {
                return CollectionPlus.asMap(element,1);
            }
        });
        Map<String,Integer> result=bucketHashMap.reduce(new Reducer<Integer, String, Integer>() {
            @Override
            public Integer reduce(String key, List<Integer> bucket) {
                int sum=0;
                for (Integer value: bucket){
                    sum+=value;
                }
                return sum;
            }
        });
        Assert.assertEquals(2,result.get("world").intValue());
        Assert.assertEquals(1,result.get("is").intValue());
    }
}
