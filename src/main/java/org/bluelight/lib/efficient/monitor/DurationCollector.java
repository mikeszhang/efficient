package org.bluelight.lib.efficient.monitor;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * record run time for various code fragments.
 * Created by mikes on 14-9-19.
 */
public class DurationCollector {
    private List<Pair<String,Long>> timePointList=new LinkedList<Pair<String, Long>>();
    public void addTimePoint(Object tag){
        Pair<String,Long> timePoint=Pair.of(tag.toString(),System.currentTimeMillis());
        this.timePointList.add(timePoint);
    }

    public void addTimePoint(){
        int pos=this.timePointList.size()+1;
        this.addTimePoint("Time point "+pos);
    }

    public List<Pair<String,Long>> collect(Object tag){
        this.addTimePoint(tag);
        List<Pair<String,Long>> durationList=new LinkedList<Pair<String,Long>>();
        if (this.timePointList.size()<2){
            return durationList;
        }
        long total=0;
        Iterator<Pair<String,Long>> iterator=this.timePointList.iterator();
        Pair<String,Long> begin=iterator.next();
        while (iterator.hasNext()){
            Pair<String,Long> end=iterator.next();
            Pair<String,Long> duration=Pair.of(begin.getLeft()+" ~ "+end.getLeft(),end.getRight()-begin.getRight());
            total+=duration.getRight();
            durationList.add(duration);
            begin=end;
        }
        durationList.add(Pair.of("total",total));
        this.timePointList.clear();
        return durationList;
    }

    public List<Pair<String,Long>> collect(){
        int pos=this.timePointList.size()+1;
        return this.collect("Time point "+pos);
    }
}
