package io.kongcode.hatchery;

import javax.inject.Provider;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by JoãoPedro on 13/08/2015.
 */
class InjectorImpl implements Injector {

    private static InjectorImpl instance;

    private final ProviderContext providerContext;

    private final InjectAnnotationScan injectAnnotationScan;

    private InjectorImpl() {
        try {
            injectAnnotationScan = new InjectAnnotationScan();
            providerContext = new ProviderContext();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static InjectorImpl getInstance() {
        if (instance == null) {
            instance = new InjectorImpl();
        }
        return instance;
    }

    @Override public <T> T create(Class<T> clazz) {
        T t = (T) providerContext.getForClass(clazz).orElse(this.getNew(clazz)).get();
        loadFields(clazz, t);
        return t;
    }

    private void loadFields(Class<?> clazz, Object t) {
        Stream.of(clazz.getDeclaredFields()).filter(injectAnnotationScan::isAnnotatedWithInject)
            .forEach(field -> {
                this.setFieldValue(t, field, providerContext.getForClass(field.getType()));
                this.loadFields(field.getClass(), getFieldValue(t, field));
            });

    }

    private Object getFieldValue(Object t, Field field) {
        try {
            field.setAccessible(true);
            Object o = field.get(t);
            field.setAccessible(false);
            return o;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFieldValue(Object t, Field field, Optional<Provider<?>> provider) {
        if (provider.isPresent()) {
            field.setAccessible(true);
            try {
                field.set(t, provider.get().get());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
    }

    public Provider<?> getNew(Class<?> clazz) {
        return () -> {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }
}



