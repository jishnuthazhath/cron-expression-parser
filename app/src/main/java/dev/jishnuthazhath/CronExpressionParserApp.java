package dev.jishnuthazhath;

import dev.jishnuthazhath.service.CronService;

public class CronExpressionParserApp {
    /**
     * This is the starting point of the application.
     * The arguments must be in the following format as a single string.
     * 0/15 0 1,15 * 1-5 /usr/bin/find
     * Prints an output to the standard out

     * minute 0 15 30 45
     * hour 0
     * day of month 1 15
     * month 1 2 3 4 5 6 7 8 9 10 11 12
     * day of week 1 2 3 4 5
     * command /usr/bin/find
     */
    public static void main(String... args) {
        if (args == null || args.length == 0) {
            System.out.println("Arguments cannot be empty!");
            return;
        }

        CronService service = new CronService();
        System.out.println(
                service.parseCronExpressionTableFormat(args[0])
        );
    }
}
