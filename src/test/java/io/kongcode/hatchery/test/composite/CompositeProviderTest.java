package io.kongcode.hatchery.test.composite;

import io.kongcode.hatchery.Injector;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * Created by JoãoPedro on 14/08/2015.
 */
public class CompositeProviderTest {

    @Test public void testCustomProviderInjection() {
        Injector instance = Injector.instance();
        long start = System.currentTimeMillis();
        CompositeProviderTestClass testClass = instance.create(CompositeProviderTestClass.class);
        long end = System.currentTimeMillis();
        assertNotNull(testClass.getA());
        assertNotNull(testClass.getB());
        System.out.println("Executed in " + (end - start) + "ms");
    }
}
