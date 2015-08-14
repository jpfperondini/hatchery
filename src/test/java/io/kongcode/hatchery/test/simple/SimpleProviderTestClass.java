package io.kongcode.hatchery.test.simple;

import io.kongcode.hatchery.test.core.A;

import javax.inject.Inject;

/**
 * Created by JoãoPedro on 13/08/2015.
 */
public class SimpleProviderTestClass {

    @Inject private A dependency;

    public A getDependency() {
        return dependency;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SimpleProviderTestClass that = (SimpleProviderTestClass) o;

        return !(dependency != null ?
            !dependency.equals(that.dependency) :
            that.dependency != null);

    }

    @Override public int hashCode() {
        return dependency != null ? dependency.hashCode() : 0;
    }
}
