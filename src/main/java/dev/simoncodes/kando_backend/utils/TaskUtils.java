package dev.simoncodes.kando_backend.utils;

import java.util.Arrays;

public class TaskUtils {
    public static <E extends Enum<E>> E parseEnumIgnoreCase(Class<E> type, String raw) {
        try {
            return Enum.valueOf(type, raw.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            String validValues = Arrays.toString(type.getEnumConstants());
            throw new IllegalArgumentException("Invalid " + type.getSimpleName() + ": " + raw +
                    ". Must be one of " + validValues);
        }
    }

}
