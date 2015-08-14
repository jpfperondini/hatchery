package io.kongcode.hatchery.provider.custom;

/**
 * Created by JoãoPedro on 13/08/2015.
 */
public class CustomProviderTestClassDependency {

    private String test;

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        CustomProviderTestClassDependency that = (CustomProviderTestClassDependency) o;

        return !(test != null ? !test.equals(that.test) : that.test != null);

    }

    @Override public int hashCode() {
        return test != null ? test.hashCode() : 0;
    }
}
