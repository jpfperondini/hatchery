package io.kongcode.hatchery.provider.custom;

import javax.inject.Provider;

/**
 * Created by JoãoPedro on 13/08/2015.
 */
public class CustomProviderTestClassDependencyProvider
    implements Provider<CustomProviderTestClassDependency> {

    @Override public CustomProviderTestClassDependency get() {
        return new CustomProviderTestClassDependency();
    }

}
