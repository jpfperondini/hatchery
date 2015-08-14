package io.kongcode.hatchery;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.TypeToken;
import io.kongcode.hatchery.provider.AbstractProvider;
import io.kongcode.hatchery.provider.ProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by JoãoPedro on 14/08/2015.
 */
class ProviderContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderContext.class);

    private final Map<String, Provider<?>> providers;

    private final ClassPath classPath;

    private final InjectAnnotationScan injectAnnotationScan;

    public ProviderContext() throws IOException {
        injectAnnotationScan = new InjectAnnotationScan();
        providers = new HashMap<>();
        classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
        classPath.getTopLevelClassesRecursive("io.kongcode.hatchery.test").stream()
            .flatMap(classInfo -> findFieldAndProvider(classInfo.load())).
            forEach(r -> r.ifPresent(q -> this.put(q, ProviderFactory.SINGLETON)));


        LOGGER.info("Context initialized");
        providers.forEach((k, v) -> LOGGER.info("{} -> {}", k, v));
    }

    private void put(FieldAndProvider fieldAndProvider, ProviderFactory providerFactory) {
        LOGGER.debug("Put {} in the context as {}", fieldAndProvider.field.getName(),
            providerFactory);

        this.providers.put(fieldAndProvider.field.getName(),
            providerFactory.create(fieldAndProvider.provider));
    }

    public Optional<Provider<?>> getForClass(Class<?> clazz) {
        return Optional.ofNullable(providers.get(clazz.getName()));
    }

    private <T> Stream<Optional<FieldAndProvider>> findFieldAndProvider(Class<T> clazz) {
        return Stream.of(clazz.getDeclaredFields())
            .filter(injectAnnotationScan::isAnnotatedWithInject).map(this::findProviders)
            .distinct();
    }


    private Optional<FieldAndProvider> findProviders(Field field) {
        LOGGER.debug("Searching provider for field with class {}", field.getType().getName());
        Optional<FieldAndProvider> fieldAndProvider =
            classPath.getTopLevelClasses(field.getType().getPackage().getName()).stream()
                .map(temp -> temp.load()).filter(q -> isProviderImplementation(q, field))
                .findFirst().map(clazz -> new FieldAndProvider(clazz, field.getType()));
        return fieldAndProvider;
    }

    private boolean isProviderImplementation(final Class<?> info, final Field field) {
        if (AbstractProvider.class.equals(info.getSuperclass())) {
            Type genericSuperclass = info.getGenericSuperclass();
            return genericSuperclass instanceof ParameterizedType
                && ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]
                .equals(field.getType());
        } else {
            return false;
        }
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
