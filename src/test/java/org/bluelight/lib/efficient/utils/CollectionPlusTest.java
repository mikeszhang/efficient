package org.bluelight.lib.efficient.utils;

import org.apache.commons.lang3.tuple.Pair;
import org.bluelight.lib.efficient.utils.funcs.ElementProcessor;
import org.bluelight.lib.efficient.utils.funcs.KVProcessor;
import org.bluelight.lib.efficient.utils.funcs.Mapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * collection include array plus test.
 * Created by mikes on 15-3-16.
 */
public class CollectionPlusTest {
    @Test
    public void testUtils() {
        Set<Integer> set= CollectionPlus.asSet(23);
        Assert.assertTrue(!set.isEmpty());
        String[] strs={"sda","ppl"};
        String[] newStrs= CollectionPlus.add(String.class, strs, "add");
        Assert.assertArrayEquals(new String[]{"sda","ppl","add"},newStrs);
        String result=CollectionPlus.foreach(newStrs, "", new ElementProcessor<String, String>() {
            @Override
            public String process(int index, String element, String currentState) {
                return currentState+element+index;
            }
        });
        Assert.assertEquals("sda0ppl1add2",result);
        Map<String,Integer> moneyMap=MapPlus.asMap("mikes",100,"jack",400,"woods",1000);
        Integer total=CollectionPlus.foreach(moneyMap, 0, new KVProcessor<String, Integer, Integer>() {
            @Override
            public Integer process(String key, Integer value, Integer state) {
                Integer total=value+state;
                if (total>600){
                    CollectionPlus.quitForeach();
                }
                return total;
            }
        });
        Assert.assertTrue(total<1500);
    }
    @Test
    public void testCount(){
        List<Pair<Integer,String>> recordList=new ArrayList<Pair<Integer, String>>();
        recordList.add(Pair.of(123, "mikes"));
        recordList.add(Pair.of(124, "jack"));
        recordList.add(Pair.of(125, "mikes"));
        BagMapAdaptor<String> bagMapAdaptor=CollectionPlus.count(recordList, new Mapper<String, Integer, Pair<Integer, String>>() {
            @Override
            public Map<String, Integer> map(Pair<Integer, String> element) {
                return MapPlus.asMap(element.getRight(),1);
            }
        });
        Assert.assertEquals(Integer.valueOf(2), bagMapAdaptor.get("mikes"));
        Assert.assertEquals(Integer.valueOf(1), bagMapAdaptor.get("jack"));
    }

    @Test
    public void testCollectionNull(){
        List<String> list=new ArrayList<String>();
        list.add("dasd");
        boolean a=CollectionPlus.addIfNotNull(list, null);
        boolean b=CollectionPlus.addIfNotNull(list, "sda");
        Assert.assertTrue(b);
        Assert.assertFalse(a);
        Assert.assertEquals(2, list.size());
        list.add(null);
        list.add(null);
        Assert.assertEquals(4,list.size());
        CollectionPlus.trimNull(list);
        Assert.assertEquals(2,list.size());
    }
}
