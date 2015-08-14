package io.kongcode.hatchery.test.core;

import io.kongcode.hatchery.provider.AbstractProvider;

/**
 * Created by JoãoPedro on 14/08/2015.
 */
public class ProviderC extends AbstractProvider<C> {
    @Override public C get() {
        return new C();
    }
}
