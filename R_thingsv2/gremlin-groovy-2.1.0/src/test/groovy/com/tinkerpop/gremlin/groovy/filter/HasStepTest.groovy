package com.tinkerpop.gremlin.groovy.filter

import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory
import com.tinkerpop.gremlin.Tokens
import com.tinkerpop.gremlin.groovy.Gremlin
import com.tinkerpop.gremlin.test.ComplianceTest

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
class HasStepTest extends com.tinkerpop.gremlin.test.filter.HasStepTest {

    Graph g = TinkerGraphFactory.createTinkerGraph();
    static {
        Gremlin.load();
    }

    public void testCompliance() {
        ComplianceTest.testCompliance(this.getClass());
    }

    public void test_g_V_hasXname_markoX() {
        super.test_g_V_hasXname_markoX(g.V.has('name', 'marko'));
    }

    public void test_g_V_hasXname_blahX() {
        super.test_g_V_hasXname_blahX(g.V.has('name', 'blah'));
    }

    public void test_g_V_hasXblah_nullX() {
        super.test_g_V_hasXblah_nullX(g.V.has('blah', null));
    }

    public void test_g_v1_out_hasXid_2X() {
        super.test_g_v1_out_hasXid_2X(g.v(1).out.has('id', '2'));
    }

    public void test_g_V_hasXage_gt_30X() {
        super.test_g_V_hasXage_gt_30X(g.V.has('age', Tokens.T.gt, 30));
    }
}
