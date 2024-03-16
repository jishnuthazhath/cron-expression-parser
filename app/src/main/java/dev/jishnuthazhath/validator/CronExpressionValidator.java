package dev.jishnuthazhath.validator;

import dev.jishnuthazhath.model.CronType;

import java.util.regex.Pattern;

public class CronExpressionValidator {
    private static final Pattern minutePattern = Pattern.compile("^(\\*|([0-5]?\\d)(-([0-5]?\\d))?)(/\\d+)?(,(\\*|([0-5]?\\d)(-([0-5]?\\d))?)(/\\d+)?)*$");
    private static final Pattern hourPattern = Pattern.compile("^(\\*|(2[0-3]|[01]?[0-9])(-(2[0-3]|[01]?[0-9]))?)(/\\d+)?(,(\\*|(2[0-3]|[01]?[0-9])(-(2[0-3]|[01]?[0-9]))?)(/\\d+)?)*$");
    private static final Pattern dayOfTheMonthPattern = Pattern.compile("^(\\*|(3[01]|[12]?[1-9]|[12]\\d)(-(3[01]|[12]?[1-9]|[12]\\d))?)(\\/\\d+)?(,(\\*|(3[01]|[12]?[1-9]|[12]\\d)(-(3[01]|[12]?[1-9]|[12]\\d))?)(\\/\\d+)?)*$");
    private static final Pattern monthPattern = Pattern.compile("^(\\*|(1[0-2]|[1-9])(-(1[0-2]|[1-9]))?)(/\\d+)?(,(\\*|(1[0-2]|[1-9])(-(1[0-2]|[1-9]))?)(/\\d+)?)*$");
    private static final Pattern weekPattern = Pattern.compile("^(\\*|([0-7])(-([0-7]))?)(/\\d+)?(,(\\*|([0-7])(-([0-7]))?)(/\\d+)?)*$");

    public boolean isValid(CronType type, String cronSubString) {
        if (cronSubString == null || cronSubString.isEmpty()) {
            return false;
        }

        if (type.equals(CronType.MINUTE)) {
            return minutePattern.matcher(cronSubString).matches();
        } else if (type.equals(CronType.HOUR)) {
            return hourPattern.matcher(cronSubString).matches();
        } else if (type.equals(CronType.DAY_OF_MONTH)) {
            return dayOfTheMonthPattern.matcher(cronSubString).matches();
        } else if (type.equals(CronType.MONTH)) {
            return monthPattern.matcher(cronSubString).matches();
        }else if (type.equals(CronType.DAY_OF_WEEK)) {
            return weekPattern.matcher(cronSubString).matches();
        } else {
            return false;
        }
    }
}
