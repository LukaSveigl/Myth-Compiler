package org.mythc.compiler.common.logging;

import org.mythc.common.logging.AnsiColors;
import org.mythc.common.logging.BaseLogger;

/**
 * The compiler logger implementation. This class contains methods for printing messages to the console that are
 * specific to the compiler.
 */
public class CompilerLogger extends BaseLogger {

    /**
     * Prints an info message to the console.
     *
     * @param location The location of the message.
     * @param message  The message.
     */
    public static void info(Location location, String message) {
        System.out.println(AnsiColors.BOLD_WHITE + "[" + location + "]" + AnsiColors.RESET + " " + message);
    }

    /**
     * Prints a warning message to the console.
     *
     * @param location The location of the message.
     * @param message  The message.
     */
    public static void warn(Location location, String message) {
        System.out.println(AnsiColors.BOLD_YELLOW + "[" + location + "]" + AnsiColors.RESET + " " + AnsiColors.YELLOW +
                message + AnsiColors.RESET);
    }

    /**
     * Prints an error message to the console.
     *
     * @param location The location of the message.
     * @param message  The message.
     */
    public static void error(Location location, String message) {
        System.out.println(AnsiColors.BOLD_RED + "[" + location + "]" + AnsiColors.RESET + " " + AnsiColors.RED +
                message + AnsiColors.RESET);
    }

}
