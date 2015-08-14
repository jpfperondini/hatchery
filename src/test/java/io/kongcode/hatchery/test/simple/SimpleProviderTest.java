package io.kongcode.hatchery.test.simple;

import io.kongcode.hatchery.Injector;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by JoãoPedro on 13/08/2015.
 */
public class SimpleProviderTest {

    @Test public void testCustomProviderInjection() {
        Injector instance = Injector.instance();
        long start = System.currentTimeMillis();
        SimpleProviderTestClass testClass = instance.create(SimpleProviderTestClass.class);
        long time = System.currentTimeMillis() - start;
        assertNotNull(testClass.getDependency());
        LoggerFactory.getLogger(SimpleProviderTest.class).info("Executed in {} ms", time);
    }

    @Test public void testCustomProviderInjectionIsSingleton() {
        Injector instance = Injector.instance();
        SimpleProviderTestClass testClass = instance.create(SimpleProviderTestClass.class);
        SimpleProviderTestClass testClass2 = instance.create(SimpleProviderTestClass.class);
        assertEquals(testClass, testClass2);
    }
}
