package com.teamhide.kream.coupon.domain;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ConditionConverterTest {
    private ConditionConverter conditionConverter = new ConditionConverter();

    @Test
    @DisplayName("ConditionValue를 ConditionValueType에 맞게 변환 요청하면 변환된 값이 리턴된다")
    void testGetTypedValue_Boolean() {
        // Given
        final String conditionValue = "true";

        // When
        final Boolean sut =
                conditionConverter.getTypedValue(conditionValue, ConditionValueType.BOOLEAN, Boolean.class);

        // Then
        assertThat(sut).isTrue();
    }

    @Test
    @DisplayName("LIST_LONG 타입을 변환하면 변환된 값이 리턴된다")
    void testGetTypedValue_ListLong() {
        // Given
        final String conditionValue = "[1, 2, 3]";

        // When
        final List<Long> result =
                conditionConverter.getTypedValue(conditionValue, ConditionValueType.LIST_LONG, List.class);

        // Then
        assertThat(result.containsAll(List.of(1L, 2L, 3L))).isTrue();
    }

    @Test
    @DisplayName("LIST_LONG 타입 변환 시 ConditionValue가 [ 로 시작하지 않는 경우 예외가 발생한다")
    void testGetTypedValue_ListLong_StartsWithBracket() {
        // Given
        final String conditionValue = "1, 2, 3]";

        // When, Then
        assertThrows(
                InvalidConditionTypeException.class,
                () ->
                        conditionConverter.getTypedValue(
                                conditionValue, ConditionValueType.LIST_LONG, List.class));
    }

    @Test
    @DisplayName("LIST_LONG 타입 변환 시 ConditionValue가 ] 로 끝나지 않는 경우 예외가 발생한다")
    void testGetTypedValue_ListLong_EndsWithBracket() {
        // Given
        final String conditionValue = "[1, 2, 3";

        // When, Then
        assertThrows(
                InvalidConditionTypeException.class,
                () ->
                        conditionConverter.getTypedValue(
                                conditionValue, ConditionValueType.LIST_LONG, List.class));
    }

    @Test
    @DisplayName("LIST_LONG 타입 변환 후 결과가 빈 리스트라면 예외가 발생한다")
    void testGetTypedValue_ListLong_EmptyList() {
        // Given
        final String conditionValue = "[]";

        // When, Then
        assertThrows(
                InvalidConditionTypeException.class,
                () ->
                        conditionConverter.getTypedValue(
                                conditionValue, ConditionValueType.LIST_LONG, List.class));
    }
}
