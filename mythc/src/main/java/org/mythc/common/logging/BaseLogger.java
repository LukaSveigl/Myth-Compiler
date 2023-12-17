package org.mythc.common.logging;

import java.io.Serializable;

/**
 * The base logger implementation. This class contains basic methods for printing messages to the console and is later
 * extended by other, more specialized loggers.
 */
public class BaseLogger {

    /**
     * Prints an info message to the console.
     *
     * @param message The message.
     */
    public static void info(String message) {
        System.out.println(message);
    }

    /**
     * Prints a warning message to the console.
     *
     * @param message The message.
     */
    public static void warn(String message) {
        System.out.println(AnsiColors.YELLOW + message + AnsiColors.RESET);
    }

    /**
     * Prints an error message to the console.
     *
     * @param message The message.
     */
    public static void error(String message) {
        System.out.println(AnsiColors.RED + message + AnsiColors.RESET);
        System.exit(1);
    }

    /**
     * Prints an error message to the console.
     *
     * @param message      The message.
     * @param serializable The serializable object to print.
     */
    public static void error(String message, Serializable serializable) {
        System.out.println(AnsiColors.RED + message + AnsiColors.RESET);
        System.out.println(serializable);
        System.exit(1);
    }

    /**
     * Prints an internal error message to the console.
     */
    public static void internalError() {
        System.out.println(AnsiColors.RED + "Internal error." + AnsiColors.RESET);
        System.exit(1);
    }

    /**
     * Prints an internal error message to the console.
     *
     * @param message The message.
     */
    public static void internalError(String message) {
        System.out.println(AnsiColors.RED + message + AnsiColors.RESET);
        System.exit(1);
    }

}
