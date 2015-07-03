package org.bluelight.lib.efficient.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * multiple map provided two keys: primary key and secondary key.
 * Created by mikes on 15/7/2.
 */
public class MultipleMap<K1,K2,V> extends HashMap<K1,Map<K2,V>> {
    public V get(K1 primaryKey, K2 secondaryKey){
        Map<K2,V> map=this.get(primaryKey);
        if (map==null){
            return null;
        }
        return map.get(secondaryKey);
    }
    public V put(K1 primaryKey, K2 secondaryKey, V value){
        Map<K2,V> map=this.get(primaryKey);
        if (map==null){
            map=new HashMap<K2, V>();
            this.put(primaryKey,map);
        }
        return map.put(secondaryKey,value);
    }
    public boolean containsKey(K1 primaryKey, K2 secondaryKey){
        if (this.containsKey(primaryKey)){
            Map<K2,V> map=this.get(primaryKey);
            return map!=null && map.containsKey(secondaryKey);
        }
        return false;
    }
    public V remove(K1 primaryKey, K2 secondaryKey){
        Map<K2,V> map=this.get(primaryKey);
        if (map!=null){
            return map.remove(secondaryKey);
        }
        return null;
    }
    public Collection<V> values(K1 primaryKey){
        Map<K2,V> map=this.get(primaryKey);
        if (map!=null){
            return map.values();
        }
        return Collections.emptyList();
    }
}
