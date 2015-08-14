package io.kongcode.hatchery.test.core;

import io.kongcode.hatchery.provider.AbstractProvider;

import javax.inject.Provider;

/**
 * Created by JoãoPedro on 14/08/2015.
 */
public class ProviderA extends AbstractProvider<A> {
    @Override public A get() {
        return new A();
    }
}
