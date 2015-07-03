package org.bluelight.lib.efficient.utils;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * test trie and its default implementation.
 * Created by mikes on 15/7/10.
 */
public class TrieTest {
    @Test
    public void testTrie(){
        Runtime.getRuntime().gc();
        long free1=Runtime.getRuntime().freeMemory();

        Set<String> set=new HashSet<String>();
//        List<String> set=new ArrayList<String>();
        for (int i=100000;i>0;i--){
            set.add(String.valueOf(i));
        }
        /*
        Trie<String> trie=new DefaultTrie<String>();
        for (int i=10000000;i>0;i--){
            trie.add(String.valueOf(i));
        }*/
        System.gc();
        Runtime.getRuntime().gc();
        long free2=Runtime.getRuntime().freeMemory();
        long used=free1-free2;
        System.out.print(used);
    }
}
