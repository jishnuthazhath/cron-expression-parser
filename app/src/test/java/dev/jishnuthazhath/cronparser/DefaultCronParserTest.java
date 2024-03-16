package dev.jishnuthazhath.cronparser;

import dev.jishnuthazhath.exception.CronParseException;
import dev.jishnuthazhath.exception.CronValidationException;
import dev.jishnuthazhath.model.CronParsedResult;
import dev.jishnuthazhath.validator.CronExpressionValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DefaultCronParserTest {
    private final DefaultCronParser defaultCronParser = new DefaultCronParser(new CronExpressionValidator());
    public static Stream<Arguments> invalidMinutePatterns() {
        return Stream.of(
                arguments("** * * * * /usr/bin/find"),
                arguments("*/20-30 * * * * /usr/bin/find"),
                arguments("100 * * * * /usr/bin/find"),
                arguments("60 * * * * /usr/bin/find"),
                arguments("-1 * * * * /usr/bin/find"),
                arguments("10/5/5 * * * * /usr/bin/find"),
                arguments("10/* * * * * /usr/bin/find")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidMinutePatterns")
    @DisplayName("Test for invalid minute patterns")
    public void shouldThrowExceptionForInvalidMinuteExpression(String inputArg) {
        assertThrows(CronValidationException.class, () -> defaultCronParser.parse(inputArg), "Invalid minute expression!");
    }

    public static Stream<Arguments> invalidHourPatterns() {
        return Stream.of(
                arguments("* ** * * * /usr/bin/find"),
                arguments("* */20-30 * * * /usr/bin/find"),
                arguments("* 100 * * * /usr/bin/find"),
                arguments("* 60 * * * /usr/bin/find"),
                arguments("* -1 * * * /usr/bin/find")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidHourPatterns")
    @DisplayName("Test for invalid hour patterns")
    public void shouldThrowExceptionForInvalidHourExpression(String inputArg) {
        assertThrows(CronValidationException.class, () -> defaultCronParser.parse(inputArg), "Invalid hour expression!");
    }

    public static Stream<Arguments> invalidDayOfMonthPatterns() {
        return Stream.of(
                arguments("* * ** * * /usr/bin/find"),
                arguments("* * */20-30 * * /usr/bin/find"),
                arguments("* * 100 * * /usr/bin/find"),
                arguments("* * 60 * * /usr/bin/find"),
                arguments("* * -1 * * /usr/bin/find")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidDayOfMonthPatterns")
    @DisplayName("Test for invalid day of the month patterns")
    public void shouldThrowExceptionForInvalidDayOfMonthExpression(String inputArg) {
        assertThrows(CronValidationException.class, () -> defaultCronParser.parse(inputArg), "Invalid day of the month expression!");
    }

    public static Stream<Arguments> invalidMonthPatterns() {
        return Stream.of(
                arguments("* * * ** * /usr/bin/find"),
                arguments("* * * */20-30 * /usr/bin/find"),
                arguments("* * * 100 * /usr/bin/find"),
                arguments("* * * 60 * /usr/bin/find"),
                arguments("* * * -1 * /usr/bin/find")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidMonthPatterns")
    @DisplayName("Test for invalid month patterns")
    public void shouldThrowExceptionForInvalidMonthExpression(String inputArg) {
        assertThrows(CronValidationException.class, () -> defaultCronParser.parse(inputArg), "Invalid month expression!");
    }

    public static Stream<Arguments> invalidWeekPatterns() {
        return Stream.of(
                arguments("* * * * ** /usr/bin/find"),
                arguments("* * * * */20-30 /usr/bin/find"),
                arguments("* * * * 100 /usr/bin/find"),
                arguments("* * * * 60 /usr/bin/find"),
                arguments("* * * * -1 /usr/bin/find"),
                arguments("* * * * 10/5/5 /usr/bin/find"),
                arguments("* * * * 10/* /usr/bin/find")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidWeekPatterns")
    @DisplayName("Test for invalid minute patterns")
    public void shouldThrowExceptionForInvalidWeekExpression(String inputArg) {
        assertThrows(CronValidationException.class, () -> defaultCronParser.parse(inputArg), "Invalid day of week expression!");
    }

    public static Stream<Arguments> minuteTestArgumentsProvider() {
        return Stream.of(
                arguments("5/4", Arrays.asList(5, 9, 13, 17, 21, 25, 29, 33, 37, 41, 45, 49, 53, 57)),
                arguments("*/15", Arrays.asList(0, 15, 30, 45)),
                arguments("*", Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59)),
                arguments("10-20", Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)),
                arguments("10,20", Arrays.asList(10, 20)),
                arguments("10-20,30-40", Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40)),
                arguments("10-20/5", Arrays.asList(10, 15, 20)),
                arguments("10,20,30", Arrays.asList(10, 20, 30)),
                arguments("5-10,10-20,30-40", Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40)),
                arguments("10-20,30-40/3", Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 30, 33, 36, 39)),
                arguments("10/5,20/3", Arrays.asList(10, 15, 20, 23, 25, 26, 29, 30, 32, 35, 38, 40, 41, 44, 45, 47, 50, 53, 55, 56, 59)),
                arguments("10-20/5,30-40/5", Arrays.asList(10, 15, 20, 30, 35, 40)),
                arguments("1", Arrays.asList(1)),
                arguments("10-10", Arrays.asList(10))
        );
    }

    @ParameterizedTest
    @MethodSource("minuteTestArgumentsProvider")
    @DisplayName("Test minute parser")
    public void shouldReturnValidMinuteValue(String input, List<Integer> expected) {
        List<Integer> actual = defaultCronParser.parseMinute(input);
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> hourTestArgumentsProvider() {
        return Stream.of(
                arguments("5/4", Arrays.asList(5,9,13,17,21)),
                arguments("*/15", Arrays.asList(0,15)),
                arguments("*", Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23)),
                arguments("10-20", Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)),
                arguments("10,20", Arrays.asList(10, 20)),
                arguments("5-10,15-20", Arrays.asList(5, 6, 7, 8, 9, 10, 15, 16, 17, 18, 19, 20)),
                arguments("10-20/5", Arrays.asList(10, 15, 20)),
                arguments("5,10,15,20,23", Arrays.asList(5, 10, 15, 20, 23)),
                arguments("5-10,10-15,15-23", Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23)),
                arguments("5-10,15-23/3", Arrays.asList(5, 6, 7, 8, 9, 10, 15, 18, 21)),
                arguments("10/5,20/3", Arrays.asList(10, 15, 20, 23)),
                arguments("5-10/3,15-20/5", Arrays.asList(5, 8, 15, 20)),
                arguments("5", Arrays.asList(5)),
                arguments("*/12", Arrays.asList(0,12))
        );
    }

    @ParameterizedTest
    @MethodSource("hourTestArgumentsProvider")
    @DisplayName("Test hour parser")
    public void shouldReturnValidHourValue(String input, List<Integer> expected) {
        List<Integer> actual = defaultCronParser.parseHour(input);
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> dayOfMonthTestArgumentsProvider() {
        return Stream.of(
                arguments("5/4", Arrays.asList(5,9,13,17,21,25,29)),
                arguments("*/15", Arrays.asList(1,16,31)),
                arguments("*", Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31)),
                arguments("1-12", Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12)),
                arguments("10,12", Arrays.asList(10,12)),
                arguments("2-5,10-12", Arrays.asList(2,3,4,5,10,11,12)),
                arguments("10-20/5", Arrays.asList(10,15,20)),
                arguments("5,10,11,12", Arrays.asList(5,10,11,12)),
                arguments("5-7,8-10,10-12", Arrays.asList(5,6,7,8,9,10,11,12)),
                arguments("5-7,8-12/3", Arrays.asList(5,6,7,8,11)),
                arguments("2/3,10/2", Arrays.asList(2, 5, 8, 10, 11, 12, 14, 16, 17, 18, 20, 22, 23, 24, 26, 28, 29, 30)),
                arguments("10/5,10/5", Arrays.asList(10, 15, 20, 25, 30)),
                arguments("5", List.of(5)),
                arguments("*/12", Arrays.asList(1,13,25))
                );
    }

    @ParameterizedTest
    @MethodSource("dayOfMonthTestArgumentsProvider")
    @DisplayName("Test day of the month parser")
    public void shouldReturnValidDayOfMonthValue(String input, List<Integer> expected) {
        List<Integer> actual = defaultCronParser.parseDayOfMonth(input);
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> monthTestArgumentsProvider() {
        return Stream.of(
                arguments("5/4", Arrays.asList(5, 9)),
                arguments("*/12", Arrays.asList(1)), // Should it be 1 or 12
                arguments("*", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)),
                arguments("10-12", Arrays.asList(10, 11, 12)),
                arguments("5,12", Arrays.asList(5, 12)),
                arguments("1-5,7-10", Arrays.asList(1, 2, 3, 4, 5, 7, 8, 9, 10)),
                arguments("5-12/5", Arrays.asList(5, 10)),
                arguments("1,2,3,4,5", Arrays.asList(1, 2, 3, 4, 5)),
                arguments("1-5,6-10,10-12", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)),
                arguments("5-7,8-12/3", Arrays.asList(5, 6, 7, 8, 11)),
                arguments("5/2,10/2", Arrays.asList(5, 7, 9, 10, 11, 12)),
                arguments("5/2,5/2", Arrays.asList(5, 7, 9, 11)), // There are duplicates, should we get rid of them ?
                arguments("1-7/3,8-12/2", Arrays.asList(1, 4, 7, 8, 10, 12)),
                arguments("5", Arrays.asList(5))
        );
    }

    @ParameterizedTest
    @MethodSource("monthTestArgumentsProvider")
    @DisplayName("Test month parser")
    public void shouldReturnValidMonthValue(String input, List<Integer> expected) {
        List<Integer> actual = defaultCronParser.parseMonth(input);
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> weekTestArgumentsProvider() {
        return Stream.of(
                arguments("5/4", Arrays.asList(5)),
                arguments("*/7", Arrays.asList(0, 7)),
                arguments("*", Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7)),
                arguments("2-7", Arrays.asList(2, 3, 4, 5, 6, 7)),
                arguments("1,7", Arrays.asList(1, 7)),
                arguments("0-3,5-7", Arrays.asList(0, 1, 2, 3, 5, 6, 7)),
                arguments("1-5/2", Arrays.asList(1, 3, 5)),
                arguments("1,3,4,5,6,7", Arrays.asList(1, 3, 4, 5, 6, 7)),
                arguments("0-2,3-4,5-7", Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7)),
                arguments("0-3,4/2", Arrays.asList(0, 1, 2, 3, 4, 6)),
                arguments("5/2,0/3", Arrays.asList(0, 3, 5, 6, 7)),
                arguments("5/2,5/2", Arrays.asList(5, 7)),
                arguments("0-4/2,5-7/1", Arrays.asList(0, 2, 4, 5, 6, 7)),
                arguments("5", Arrays.asList(5)),
                arguments("7-7", Arrays.asList(7))
        );
    }

    @ParameterizedTest
    @MethodSource("weekTestArgumentsProvider")
    @DisplayName("Test week parser")
    public void shouldReturnValidWeekValue(String input, List<Integer> expected) {
        List<Integer> actual = defaultCronParser.parseWeek(input);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowExceptionWhenNullArgument() {
        assertThrows(CronParseException.class, () -> defaultCronParser.parse(null));
    }

    @Test
    public void shouldThrowExceptionWhenBadArgument1() {
        assertThrows(CronParseException.class, () -> defaultCronParser.parse("* * *"));
    }

    @Test
    public void shouldThrowExceptionWhenBadArgument2() {
        assertThrows(CronParseException.class, () -> defaultCronParser.parse("* * * * * * *"));
    }

    @Test
    public void shouldReturnValidModelWhenValidArgument() {
        String expression = "*/15 0 1,15 * 1-5 /usr/bin/find";
        CronParsedResult result = defaultCronParser.parse(expression);

        assertEquals(result.getMinute(), List.of(0,15,30,45));
        assertEquals(result.getHour(), List.of(0));
        assertEquals(result.getDayOfMonth(), List.of(1,15));
        assertEquals(result.getMonth(), List.of(1,2,3,4,5,6,7,8,9,10,11,12));
        assertEquals(result.getDayOfWeek(), List.of(1,2,3,4,5));
        assertEquals(result.getCommand(), "/usr/bin/find");
    }
}