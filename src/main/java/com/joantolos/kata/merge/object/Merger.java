package com.joantolos.kata.merge.object;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class Merger {

    private final List<String> PRIMITIVE_JSON_TYPES = Arrays.asList("Long", "Long[]", "Integer", "Integer[]", "String", "String[]", "Boolean", "boolean[]", "ArrayList", "LinkedHashMap");

    @SuppressWarnings("unchecked")
    public <T> T merge(T local, T remote) throws IllegalAccessException, InstantiationException {
        Class<?> clazz = local.getClass();
        Object merged = clazz.newInstance();

        for (Field field : clazz.getDeclaredFields()) {

            field.setAccessible(true);
            Object localValue = field.get(local);
            Object remoteValue = field.get(remote);

            if (localValue == null && remoteValue != null) {
                if (PRIMITIVE_JSON_TYPES.contains(remoteValue.getClass().getSimpleName())) {
                    field.set(merged, remoteValue);
                } else {
                    field.set(merged, merge(localValue, remoteValue));
                }
            } else if (localValue != null) {
                if (PRIMITIVE_JSON_TYPES.contains(localValue.getClass().getSimpleName())) {
                    field.set(merged, localValue);
                } else {
                    field.set(merged, merge(localValue, remoteValue));
                }
            }
        }

        return (T) merged;
    }
}
