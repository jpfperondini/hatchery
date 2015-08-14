package io.kongcode.hatchery.provider.custom;

import javax.inject.Inject;

/**
 * Created by JoãoPedro on 13/08/2015.
 */
public class CustomProviderTestClass {

    @Inject private CustomProviderTestClassDependency dependency;

    public CustomProviderTestClassDependency getDependency() {
        return dependency;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        CustomProviderTestClass that = (CustomProviderTestClass) o;

        return !(dependency != null ?
            !dependency.equals(that.dependency) :
            that.dependency != null);

    }

    @Override public int hashCode() {
        return dependency != null ? dependency.hashCode() : 0;
    }
}
