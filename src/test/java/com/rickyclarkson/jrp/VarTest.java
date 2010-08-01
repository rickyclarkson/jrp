package com.rickyclarkson.jrp;

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static com.rickyclarkson.jrp.Signals.signal;
import static com.rickyclarkson.jrp.Signals.observe;

public final class VarTest extends TestCase {
    public void testVars() {
        final Var<Integer> a = new Var<Integer>(1);
        final Var<Integer> b = new Var<Integer>(2);
        Signal<Integer> sum = signal(new ByName<Integer>() {
            public Integer apply() {
                return a.apply() + b.apply();
            }
        });
        final List<Integer> results = new ArrayList<Integer>();
        observe(sum, new Effect<Integer>() {
            public void apply(Integer i) {
                results.add(i);
            }
        });
        a.update(7);
        b.update(35);
        assertEquals(results.get(0).intValue(), 9);
        assertEquals(results.get(1).intValue(), 42);        
    }
} 
