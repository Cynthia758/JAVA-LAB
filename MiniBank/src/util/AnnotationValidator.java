
package MiniBank.src.util;

import java.lang.reflect.Field;

import MiniBank.src.model.annotation.Id;
import MiniBank.src.model.annotation.Positive;
import MiniBank.src.model.annotation.MaxLength;

public final class AnnotationValidator {

    private AnnotationValidator() {
    }

    public static void validate(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }

        Class<?> current = object.getClass();

        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(object);

                    if (field.isAnnotationPresent(Id.class)) {
                        if (value == null || value.toString().isBlank()) {
                            throw new IllegalArgumentException(
                                field.getName() + " must not be empty"
                            );
                        }
                    }

                    if (field.isAnnotationPresent(Positive.class)) {
                        Positive annotation =
                            field.getAnnotation(Positive.class);

                        if (!(value instanceof Number)
                                || ((Number) value).longValue() <= 0) {
                            throw new IllegalArgumentException(
                                field.getName() + " " + annotation.message()
                            );
                        }
                    }

                    if (field.isAnnotationPresent(MaxLength.class)) {
                        MaxLength annotation =
                            field.getAnnotation(MaxLength.class);

                        if (value != null
                                && value.toString().length() > annotation.value()) {
                            throw new IllegalArgumentException(
                                field.getName() + " must not exceed "
                                + annotation.value() + " characters"
                            );
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(
                        "Unable to validate " + field.getName(), e
                    );
                }
            }

            current = current.getSuperclass();
        }
    }
}
