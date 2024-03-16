package dev.jishnuthazhath.service;

import dev.jishnuthazhath.cronparser.CronParser;
import dev.jishnuthazhath.cronparser.DefaultCronParser;
import dev.jishnuthazhath.exception.CronParseException;
import dev.jishnuthazhath.exception.CronValidationException;
import dev.jishnuthazhath.formatter.CronResultFormatter;
import dev.jishnuthazhath.formatter.DefaultCronResultFormatter;
import dev.jishnuthazhath.model.CronParsedResult;
import dev.jishnuthazhath.validator.CronExpressionValidator;

public class CronService {
    private final CronParser cronParser;
    private final CronResultFormatter<String> cronResultFormatter;

    public CronService() {
        CronExpressionValidator validator = new CronExpressionValidator();
        this.cronParser = new DefaultCronParser(validator);
        this.cronResultFormatter = new DefaultCronResultFormatter();
    }

    public CronService(CronParser parser, CronResultFormatter<String> cronResultFormatter) {
        this.cronParser = parser;
        this.cronResultFormatter = cronResultFormatter;
    }

    public String parseCronExpressionTableFormat(String cronExpression) {
        CronParsedResult result = null;
        try {
            result = cronParser.parse(cronExpression);
        } catch (CronParseException | CronValidationException ex) {
            System.out.println(ex.getMessage());
        }
        return cronResultFormatter.format(result);
    }
}
