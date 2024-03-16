package dev.jishnuthazhath.formatter;

import dev.jishnuthazhath.model.CronParsedResult;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultCronResultFormatter implements CronResultFormatter<String> {
    private static final int tableColumnSize = 14;
    @Override
    public String format(CronParsedResult result) {
        if (result == null) {
            return "";
        }

        return String.format("%s" + getColumnSpace("minute") + "%s%n", "minute", join(result.getMinute())) +
                String.format("%s" + getColumnSpace("hour") + "%s%n", "hour", join(result.getHour())) +
                String.format("%s" + getColumnSpace("day of month") + "%s%n", "day of month", join(result.getDayOfMonth())) +
                String.format("%s" + getColumnSpace("month") + "%s%n", "month", join(result.getMonth())) +
                String.format("%s" + getColumnSpace("day of week") + "%s%n", "day of week", join(result.getDayOfWeek())) +
                String.format("%s" + getColumnSpace("command") + "%s%n", "command", result.getCommand());
    }

    private String join(List<Integer> values) {
        return values.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    private String getColumnSpace(String column) {
        int space = tableColumnSize - column.length();
        return " ".repeat(Math.max(0, space));
    }
}
