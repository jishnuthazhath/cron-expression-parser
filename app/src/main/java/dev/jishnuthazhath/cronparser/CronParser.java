package dev.jishnuthazhath.cronparser;

import dev.jishnuthazhath.model.CronParsedResult;

public interface CronParser {
    CronParsedResult parse(String argument);
}
