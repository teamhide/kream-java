package com.teamhide.kream.coupon.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class ConditionConverter {
    private static final String PREFIX = "[";
    private static final String SUFFIX = "]";

    public <T> T getTypedValue(
            final String value, final ConditionValueType valueType, final Class<T> clazz) {
        Object result;

        switch (valueType) {
            case BOOLEAN -> result = toBooleanStrictOrNull(value);
            case LIST_LONG -> result = toListOfInt(value);
            default -> throw new InvalidConditionTypeException();
        }

        if (!clazz.isInstance(result)) {
            throw new InvalidConditionTypeException();
        }
        return clazz.cast(result);
    }

    private Boolean toBooleanStrictOrNull(final String value) {
        if ("true".equalsIgnoreCase(value)) {
            return true;
        } else if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        return null;
    }

    private Long toLongOrNull(final String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public List<Long> toListOfInt(final String value) {
        if (!value.startsWith(PREFIX) || !value.endsWith(SUFFIX)) {
            throw new InvalidConditionTypeException();
        }

        final String removedSurrounding = removeSurrounding(value);
        final List<Long> result =
                Arrays.stream(removedSurrounding.split(","))
                        .map(String::trim)
                        .map(this::toLongOrNull)
                        .filter(Objects::nonNull)
                        .toList();
        if (result.isEmpty()) {
            throw new InvalidConditionTypeException();
        }
        return result;
    }

    public void validateValueType(
            final ConditionType conditionType,
            final ConditionValueType conditionValueType,
            final String conditionValue) {
        switch (conditionType.getValueType()) {
            case BOOLEAN:
                getTypedValue(conditionValue, conditionValueType, Boolean.class);
                break;
            case LIST_LONG:
                getTypedValue(conditionValue, conditionValueType, List.class);
                break;
            default:
                throw new InvalidConditionTypeException();
        }
    }

    private String removeSurrounding(final String input) {
        if (input.startsWith(PREFIX) && input.endsWith(SUFFIX)) {
            return input.substring(PREFIX.length(), input.length() - SUFFIX.length());
        }
        return input;
    }
}
