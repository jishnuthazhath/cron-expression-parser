package dev.jishnuthazhath.formatter;

import dev.jishnuthazhath.model.CronParsedResult;

public interface CronResultFormatter<T> {
    T format(CronParsedResult result);
}
