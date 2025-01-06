package com.teamhide.kream.coupon.domain;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Component
public class ConditionConverter {
    private static final String PREFIX = "[";
    private static final String SUFFIX = "]";
    private static final String TRUE_STRING = "true";
    private static final String FALSE_STRING = "false";

    private final EnumMap<ConditionValueType, Function<String, Object>> typedHandler;

    public ConditionConverter() {
        this.typedHandler = new EnumMap<>(ConditionValueType.class);
        this.typedHandler.put(ConditionValueType.BOOLEAN, this::toBooleanStrictOrNull);
        this.typedHandler.put(ConditionValueType.LIST_LONG, this::toListOfInt);
    }

    public <T> T getTypedValue(
            final String value, final ConditionValueType valueType, final Class<T> clazz) {
        final Function<String, Object> handler = typedHandler.get(valueType);
        if (handler == null) {
            throw new InvalidConditionTypeException();
        }

        final Object result = handler.apply(value);
        if (!clazz.isInstance(result)) {
            throw new InvalidConditionTypeException();
        }
        return clazz.cast(result);
    }

    private Boolean toBooleanStrictOrNull(final String value) {
        if (TRUE_STRING.equalsIgnoreCase(value)) {
            return true;
        } else if (FALSE_STRING.equalsIgnoreCase(value)) {
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
