package io.kongcode.hatchery;

/**
 * Created by JoãoPedro on 13/08/2015.
 */
public interface Injector {

    static Injector instance() {
        return InjectorImpl.getInstance();
    }

    <T> T create(Class<T> clazz);
}
