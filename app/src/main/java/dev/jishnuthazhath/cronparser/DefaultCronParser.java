package dev.jishnuthazhath.cronparser;

import dev.jishnuthazhath.exception.CronParseException;
import dev.jishnuthazhath.exception.CronValidationException;
import dev.jishnuthazhath.model.CronParsedResult;
import dev.jishnuthazhath.model.CronType;
import dev.jishnuthazhath.validator.CronExpressionValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultCronParser implements CronParser{
    private final CronExpressionValidator cronExpressionValidator;

    private static final int MINUTE_MIN_VALUE = 0;
    private static final int MINUTE_MAX_VALUE = 59;
    private static final int HOUR_MIN_VALUE = 0;
    private static final int HOUR_MAX_VALUE = 23;
    private static final int DAY_OF_MONTH_MIN_VALUE = 1;
    private static final int DAY_OF_MONTH_MAX_VALUE = 31;
    private static final int MONTH_MIN_VALUE = 1;
    private static final int MONTH_MAX_VALUE = 12;
    private static final int WEEK_MIN_VALUE = 1;
    private static final int WEEK_MAX_VALUE = 7;

    public DefaultCronParser(CronExpressionValidator cronExpressionValidator) {
        this.cronExpressionValidator = cronExpressionValidator;
    }

    @Override
    public CronParsedResult parse(String argument) {

        if (argument == null || argument.isEmpty()) {
            throw new CronParseException("The argument cannot be empty");
        }

        String [] argsArr = argument.split("\\s");

        // We don't want users to send a bad expression.
        if (argsArr.length < 6) {
            throw new CronParseException("Cron expression doesn't look right.");
        }

        String minuteArg = argsArr[0];
        String hourArg = argsArr[1];
        String dayOfMonthArg = argsArr[2];
        String monthArg = argsArr[3];
        String weekArg = argsArr[4];
        String command = getCommand(argsArr, 5);

        return new CronParsedResult(
                parseMinute(minuteArg),
                parseHour(hourArg),
                parseDayOfMonth(dayOfMonthArg),
                parseMonth(monthArg),
                parseWeek(weekArg),
                command
        );
    }

    private String getCommand(String [] arr, int idx) {
        List<String> strList = new ArrayList<>();

        for (int i=idx; i<arr.length; i++) {
            strList.add(arr[i]);
        }
        return String.join(" ", strList);
    }

    /**
     *
     * @param minuteArg Minute sub-expression
     * @return A list of parsed minute values
     */
    public List<Integer> parseMinute(String minuteArg) {
        if (!cronExpressionValidator.isValid(CronType.MINUTE, minuteArg)) {
            throw new CronValidationException("Invalid minute expression!");
        }
        return sort(buildValues(minuteArg, MINUTE_MIN_VALUE, MINUTE_MAX_VALUE));
    }

    /**
     *
     * @param hourArg Hour sub-expression
     * @return A list of parsed hour values
     */
    public List<Integer> parseHour(String hourArg) {
        if (!cronExpressionValidator.isValid(CronType.HOUR, hourArg)) {
            throw new CronValidationException("Invalid hour expression!");
        }
        return sort(buildValues(hourArg, HOUR_MIN_VALUE, HOUR_MAX_VALUE));
    }

    /**
     *
     * @param dayOfMonthArg Day of the month sub-expression
     * @return A list of parsed day of the month values
     */
    public List<Integer> parseDayOfMonth(String dayOfMonthArg) {
        if (!cronExpressionValidator.isValid(CronType.DAY_OF_MONTH, dayOfMonthArg)) {
            throw new CronValidationException("Invalid day of month expression!");
        }
        return sort(buildValues(dayOfMonthArg, DAY_OF_MONTH_MIN_VALUE, DAY_OF_MONTH_MAX_VALUE));
    }

    /**
     *
     * @param monthArg Month sub-expression
     * @return A list of parsed month values
     */
    public List<Integer> parseMonth(String monthArg) {
        if (!cronExpressionValidator.isValid(CronType.MONTH, monthArg)) {
            throw new CronValidationException("Invalid month expression!");
        }
        return sort(buildValues(monthArg, MONTH_MIN_VALUE, MONTH_MAX_VALUE));
    }

    /**
     *
     * @param weekArg Week sub-expression
     * @return A list of parsed week values
     */
    public List<Integer> parseWeek(String weekArg) {
        if (!cronExpressionValidator.isValid(CronType.DAY_OF_WEEK, weekArg)) {
            throw new CronValidationException("Invalid day of week expression!");
        }
        return sort(buildValues(weekArg, WEEK_MIN_VALUE, WEEK_MAX_VALUE));
    }

    /**
     *
     * @param s The cron sub-expression. Can be minute, hour, day of the month, month, week.
     * @param minValue The minimum value each of the above can have.
     * @param maxValue The maximum value each of the above can have.
     * @return Returns a List of values, defined by the sub-expression.
     *
     * We choose to have a List here convenience. Some places in the code, the elements are directly accessed.
     * But, at the same time in some places we are removing duplicates from the list (A Set would have been a better option there)
     * In the end we are need each result sorted. If we use a Set we need to convert it to List and sort it.
     * We could use a TreeSet for a sorted set, but element insertion is costly when compared.
     *
     * 5/10 * * * * bin/find
     *
     * Note that we are dealing with a very small set of data here and using any of the above shouldn't be a concern. :D
     */
    private List<Integer> buildValues(String s, int minValue, int maxValue) {
        // All the values
        if (s.equals("*")) {
            return getValueSetWrapAround(minValue, maxValue, 1, minValue, maxValue);
        }

        // If the sub-expression contains a comma, it is listing multiple values.
        // Can have 0-many values.
        // Each of this value can be a sub-expression as well.
        if (s.contains(",")) {
            String [] valuesSplit = s.split(",");
            List<Integer> tempList = new ArrayList<>();
            for (String string : valuesSplit) {
                // Removing the duplicates. Yes. If we do not need duplicates in my datastructure we could have used a Set.
                // Why the extra iteration overhead ?. My reasoning is that, Lists keep the insertion order. I would like to keep it like that.
                // Open for discussion.
                // 5-10,4-30,
                List<Integer> values = buildValues(string, minValue, maxValue)
                        .stream()
                        .filter(x -> !tempList.contains(x))
                        .collect(Collectors.toList());;
                tempList.addAll(values);
            }
            return tempList;
        }

        // If the sub-expression contains a forward slash, it is an incrementer operator.
        // Example, 5/4. Here I am assuming that the value after the incrementer operator cannot be another sub-expression.
        // Only the value before the incrementer operator is a sub-expression.
        // 5-20/2
        if (s.contains("/")) {
            String [] incrementerSplit = s.split("/");
            List<Integer> left = buildValues(incrementerSplit[0], minValue, maxValue);
            int right = Integer.parseInt(incrementerSplit[1]);

            // When the value before the incrementer operator is another sub-expression Or a single value
            int to = left.size() == 1 ? maxValue : left.get(left.size() - 1);
            return getValueSetWrapAround(left.get(0), to, right, minValue, maxValue);
        }

        // If the sub-expression contains a dash, it is a range operator. It denotes a range of values.
        if (s.contains("-")) {
            String [] incrementerSplit = s.split("-");
            int left = Integer.parseInt(incrementerSplit[0]);
            int right = Integer.parseInt(incrementerSplit[1]);

            return getValueSetWrapAround(left, right, 1, minValue, maxValue);
        }

        // If it does not contain any of the special characters we can safely assume it is an integer
        return List.of(Integer.parseInt(s));
    }

    private List<Integer> getValueSetWrapAround(int from, int to, int incr, int minValue, int maxValue) {
        List<Integer> result = new ArrayList<>();
        if (from < minValue)
        {
            from = minValue;
        }

        if (from > to) {
           result.addAll(getValueSet(from, maxValue, incr));
           for (int i=minValue; i<=to; i++) {
               result.add(i);
           }
        } else {
            return getValueSet(from, to, incr);
        }
        return result;
    }

    private List<Integer> getValueSet(int from, int to, int incrementer) {
        List<Integer> resultList = new ArrayList<>();
        for (int i=from; i<=to; i+=incrementer) {
            resultList.add(i);
        }
        return resultList;
    }

    // Sorting the list. We are dealing with a small set of values here, so with the speed of processors these days,
    // so time complexity is not a concern here. Open for discussion.
    private List<Integer> sort(List<Integer> list) {
        if (list.size() > 1) {
            Collections.sort(list);
        }
        return list;
    }
}
