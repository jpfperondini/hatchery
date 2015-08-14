package io.kongcode.hatchery;

import com.google.common.reflect.ClassPath;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by JoãoPedro on 13/08/2015.
 */
class InjectorImpl implements Injector {

    private static InjectorImpl instance;
    private final ClassPath classPath;

    private Map<Class<?>, Object> providers = new HashMap<>();

    private InjectorImpl() {
        try {
            classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            classPath.getTopLevelClassesRecursive("io.kongcode.hatchery.provider.custom").stream()
                .map(classInfo -> findFieldAndProvider(classInfo.load())).forEach(
                fieldAndProvider -> fieldAndProvider
                    .ifPresent(r -> r.ifPresent(q -> providers.put(q.field, q.provider.get()))));

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

    private boolean isAnnotatedWithInject(Field field) {
        return Stream.of(field.getAnnotations())
            .anyMatch(annotation -> annotation instanceof Inject);
    }

    @Override public <T> T create(Class<T> clazz) {
        try {
            T t = clazz.newInstance();

            Stream.of(clazz.getDeclaredFields()).filter(this::isAnnotatedWithInject)
                .forEach(field -> this.setFieldValue(t, field, providers.get(field.getType())));

            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> Optional<Optional<FieldAndProvider>> findFieldAndProvider(Class<T> clazz) {
        return Stream.of(clazz.getDeclaredFields()).filter(this::isAnnotatedWithInject).findFirst()
            .map(this::findProviders);
    }

    private Optional<FieldAndProvider> findProviders(Field field) {
        return classPath.getTopLevelClasses(field.getType().getPackage().getName()).stream()
            .map(temp -> temp.load()).filter(this::isProviderImplementation).findFirst()
            .map(clazz -> new FieldAndProvider(clazz, field.getType()));
    }

    private boolean isProviderImplementation(Class<?> info) {
        return Stream.of(info.getInterfaces()).filter(interfac -> Provider.class.equals(interfac))
            .findFirst().isPresent();
    }

    private void setFieldValue(Object t, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(t, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        field.setAccessible(false);
    }
}


class FieldAndProvider {

    public final Provider<?> provider;

    public final Class<?> field;

    public FieldAndProvider(Class<?> providerClass, Class<?> fieldClass) {
        try {
            this.field = fieldClass;
            this.provider = (Provider<?>) providerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
