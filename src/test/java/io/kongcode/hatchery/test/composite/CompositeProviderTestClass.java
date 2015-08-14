package io.kongcode.hatchery.test.composite;

import io.kongcode.hatchery.test.core.A;
import io.kongcode.hatchery.test.core.B;

import javax.inject.Inject;

/**
 * Created by JoãoPedro on 14/08/2015.
 */
public class CompositeProviderTestClass {

    @Inject private A a;

    @Inject private B b;

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }
}
