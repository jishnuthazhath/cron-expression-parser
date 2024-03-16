package dev.jishnuthazhath.validator;

import dev.jishnuthazhath.model.CronType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CronExpressionValidatorTest {
    private final CronExpressionValidator cronExpressionValidator = new CronExpressionValidator();

    public static Stream<Arguments> invalidMinutePatterns() {
        return Stream.of(
                arguments("**", false),
                arguments("*/20-30", false),
                arguments("100", false),
                arguments("60", false),
                arguments("-1", false),
                arguments("10/5/5", false),
                arguments("10/*", false),

                arguments("59", true),
                arguments("10-20", true),
                arguments("0", true),
                arguments("10-40,30-50", true),
                arguments("", false),
                arguments(null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidMinutePatterns")
    @DisplayName("Test for invalid minute patterns")
    public void shouldThrowExceptionForInvalidMinuteExpression(String inputArg, boolean expected) {
        assertEquals(expected, cronExpressionValidator.isValid(CronType.MINUTE, inputArg));
    }

    public static Stream<Arguments> invalidHourPatterns() {
        return Stream.of(
                arguments("**", false),
                arguments("*/20-30", false),
                arguments("100", false),
                arguments("60", false),
                arguments("-1", false),
                arguments("10/5/5", false),
                arguments("10/*", false),
                arguments("24/4", false),
                arguments("5-10,10-24/4", false),

                arguments("23", true),
                arguments("", false),
                arguments("0", true),
                arguments("0-10", true),
                arguments("10-24", false),
                arguments("10-12,13-15", true)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidHourPatterns")
    @DisplayName("Test for invalid minute patterns")
    public void shouldThrowExceptionForInvalidHourExpression(String inputArg, boolean expected) {
        assertEquals(expected, cronExpressionValidator.isValid(CronType.HOUR, inputArg));
    }

    public static Stream<Arguments> invalidDayOfMonthPatterns() {
        return Stream.of(
                arguments("**", false),
                arguments("*/20-30", false),
                arguments("100", false),
                arguments("60", false),
                arguments("-1", false),
                arguments("10/5/5", false),
                arguments("10/*", false),
                arguments("32/4", false),
                arguments("5-10,10-32/4", false),
                arguments("32", false),
                arguments("31", true),
                arguments("0", false),
                arguments("1", true),
                arguments("1-31", true),
                arguments("10,12", true)

        );
    }

    @ParameterizedTest
    @MethodSource("invalidDayOfMonthPatterns")
    @DisplayName("Test for invalid day of month patterns")
    public void shouldThrowExceptionForInvalidDayOfMonthExpression(String inputArg, boolean expected) {
        assertEquals(expected, cronExpressionValidator.isValid(CronType.DAY_OF_MONTH, inputArg));
    }

    public static Stream<Arguments> invalidMonthPatterns() {
        return Stream.of(
                arguments("**", false),
                arguments("*/20-30", false),
                arguments("100", false),
                arguments("60", false),
                arguments("-1", false),
                arguments("10/5/5", false),
                arguments("10/*", false),
                arguments("32/4", false),
                arguments("5-10,10-32/4", false),
                arguments("13/2", false),
                arguments("5-10,10-13/4", false),
                arguments("12/*", false),
                arguments("13", false),
                arguments("0", false),
                arguments("1-12", true),
                arguments("12", true)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidMonthPatterns")
    @DisplayName("Test for invalid minute patterns")
    public void shouldThrowExceptionForInvalidMonthExpression(String inputArg, boolean expected) {
        assertEquals(expected, cronExpressionValidator.isValid(CronType.MONTH, inputArg));
    }

    public static Stream<Arguments> invalidWeekPatterns() {
        return Stream.of(
                arguments("**", false),
                arguments("*/20-30", false),
                arguments("100", false),
                arguments("60", false),
                arguments("-1", false),
                arguments("10/5/5", false),
                arguments("10/*", false),

                arguments("32/4", false),
                arguments("5-10,10-32/4", false),

                arguments("13/2", false),
                arguments("5-10,10-13/4", false),
                arguments("12/*", false),

                arguments("8/2", false),
                arguments("5-7,7-10/4", false),
                arguments("7/*", false),
                arguments("0", true),
                arguments("0-7", true),
                arguments("7", true),
                arguments("1-4,4-5",true)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidWeekPatterns")
    @DisplayName("Test for invalid minute patterns")
    public void shouldThrowExceptionForInvalidWeekExpression(String inputArg, boolean expected) {
        assertEquals(expected, cronExpressionValidator.isValid(CronType.DAY_OF_WEEK, inputArg));
    }
}