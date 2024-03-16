package dev.jishnuthazhath.service;

import dev.jishnuthazhath.cronparser.DefaultCronParser;
import dev.jishnuthazhath.formatter.DefaultCronResultFormatter;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


class CronServiceTest {
    private final DefaultCronParser defaultCronParser = mock(DefaultCronParser.class);
    private final DefaultCronResultFormatter cronResultFormatter = mock(DefaultCronResultFormatter.class);
    private final CronService cronService = new CronService(defaultCronParser, cronResultFormatter);

    @Test
    public void verifyCalled() {
        cronService.parseCronExpressionTableFormat("* * * * *");
        verify(defaultCronParser, times(1)).parse("* * * * *");
        verify(cronResultFormatter, times(1)).format(any());
    }
}