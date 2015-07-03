package org.bluelight.lib.efficient.logic;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mikes on 15-2-15.
 */
public class PropositionTest {
    @Test
    public void testLogic(){
        Proposition p1=new AtomicProposition("a");
        Proposition p2=new AtomicProposition("b");
        Proposition con=new Conjunction(p1,p2);
        Assert.assertTrue(new Negation(con).value(null,false));
        Proposition p=PropositionUtils.parseProposition("~a && (b || ~c) -> c");
        Map<AtomicProposition,Boolean> map=new HashMap<AtomicProposition, Boolean>();
        map.put(new AtomicProposition("a"),false);
        map.put(new AtomicProposition("b"),false);
        map.put(new AtomicProposition("c"),false);
        Assert.assertFalse(p.value(map, true));
        Assert.assertTrue(p.toCNF().isCNF());
        Assert.assertTrue(p.toDNF().isDNF());
        Assert.assertFalse(p.isCNF());
        p=PropositionUtils.parseProposition("a");
        Assert.assertEquals(new AtomicProposition("a"),p);
    }
}
