package dev.jishnuthazhath.formatter;

import dev.jishnuthazhath.model.CronParsedResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultCronResultFormatterTest {
    private final DefaultCronResultFormatter cronResultFormatter = new DefaultCronResultFormatter();

    @Test
    public void shouldFormatResult() {
        String expected = "minute        10 20\n" +
                "hour          0\n" +
                "day of month  28\n" +
                "month         7\n" +
                "day of week   0\n" +
                "command       /bin/find\n";
        String result = cronResultFormatter.format(new CronParsedResult(
                List.of(10,20),
                List.of(0),
                List.of(28),
                List.of(7),
                List.of(0),
                "/bin/find"
        ));
        System.out.println(result);
        assertEquals(expected, result);
    }
}