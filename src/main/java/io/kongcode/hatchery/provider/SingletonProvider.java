package io.kongcode.hatchery.provider;

import javax.inject.Provider;

/**
 * Created by JoãoPedro on 13/08/2015.
 */
class SingletonProvider<T> implements Provider<T> {

    private final T instance;

    SingletonProvider(T instance) {
        this.instance = instance;
    }

    @Override public T get() {
        return instance;
    }
}
