package org.bluelight.lib.efficient.monitor;


import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

/**
 * test duration.
 * Created by mikes on 14-9-19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class DurationCollectorTest {
    @Test
    public void testCollect(){
//        DurationCollector collector=new DurationCollector();
//        collector.addTimePoint(1);
//        for (int i=0;i<100000;i++){
//            int a=6*9;
//        }
//        collector.addTimePoint();
//        List<Pair<String,Long>> list=collector.collect(2);
//        Assert.assertEquals(3,list.size());
//        Assert.assertTrue(list.get(0).getRight()>0);
//        String said=ScalaTmp.say("22");
//        Assert.assertEquals("22 is said.",said);
    }

}
