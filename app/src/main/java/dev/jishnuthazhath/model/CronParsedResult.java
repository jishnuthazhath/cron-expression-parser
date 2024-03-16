package dev.jishnuthazhath.model;

import java.util.List;

public class CronParsedResult {
    private final List<Integer> minute;
    private final List<Integer> hour;
    private final List<Integer> dayOfMonth;
    private final List<Integer> month;
    private final List<Integer> dayOfWeek;
    private final String command;

    public CronParsedResult(List<Integer> minute, List<Integer> hour, List<Integer> dayOfMonth, List<Integer> month, List<Integer> dayOfWeek, String command) {
        this.minute = minute;
        this.hour = hour;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
        this.command = command;
    }

    public List<Integer> getMinute() {
        return minute;
    }

    public List<Integer> getHour() {
        return hour;
    }

    public List<Integer> getDayOfMonth() {
        return dayOfMonth;
    }

    public List<Integer> getMonth() {
        return month;
    }

    public List<Integer> getDayOfWeek() {
        return dayOfWeek;
    }

    public String getCommand() {
        return command;
    }
}
