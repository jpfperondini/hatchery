package io.kongcode.hatchery.provider.custom;

import io.kongcode.hatchery.Injector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by JoãoPedro on 13/08/2015.
 */
public class CustomProviderTest {

    @Test public void testCustomProviderInjection() {
        Injector instance = Injector.instance();
        long start = System.currentTimeMillis();
        CustomProviderTestClass testClass = instance.create(CustomProviderTestClass.class);
        long end = System.currentTimeMillis();
        assertNotNull(testClass.getDependency());
        System.out.println("Executed in " + (end - start) + "ms");
    }

    @Test public void testCustomProviderInjectionIsSingleton() {
        Injector instance = Injector.instance();
        CustomProviderTestClass testClass = instance.create(CustomProviderTestClass.class);
        CustomProviderTestClass testClass2 = instance.create(CustomProviderTestClass.class);
        assertEquals(testClass, testClass2);
    }



}
