package io.kongcode.hatchery;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.stream.Stream;

/**
 * Created by JoãoPedro on 14/08/2015.
 */
class InjectAnnotationScan {

    public boolean isAnnotatedWithInject(Field field) {
        return Stream.of(field.getAnnotations())
            .anyMatch(annotation -> annotation instanceof Inject);
    }
}
