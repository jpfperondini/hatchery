package io.kongcode.hatchery.test.core;

import io.kongcode.hatchery.provider.AbstractProvider;

import javax.inject.Provider;

/**
 * Created by JoãoPedro on 14/08/2015.
 */
public class ProviderB extends AbstractProvider<B> {
    @Override public B get() {
        return new B();
    }
}
