package io.kongcode.hatchery.provider;

import javax.inject.Provider;

/**
 * Created by JoãoPedro on 13/08/2015.
 */
public enum ProviderFactory {

    SINGLETON {
        @Override public <T> Provider<T> create(Provider<T> provider) {
            return new SingletonProvider<>(provider.get());
        }
    };

    public abstract <T> Provider<T> create(Provider<T> provider);

}
