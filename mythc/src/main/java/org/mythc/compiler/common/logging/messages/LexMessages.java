package org.mythc.compiler.common.logging.messages;

/**
 * The {@code LexMessages} class groups together all messages that are specific to the lexical analysis phase. The
 * constant messages are represented as static fields, while the dynamic messages are represented as static methods.
 */
public class LexMessages {

    /** Represents a message for when a string literal has not been closed until the end of the file. */
    public static String UNCLOSED_STRING_LITERAL = "Unclosed string literal";

    /** Represents a message for when a comment has not been closed until the end of the file. */
    public static String UNCLOSED_COMMENT_AT_EOF = "Unclosed comment at end of file";

    /**
     * Represents the message for when the source file could not be opened successfully.
     *
     * @param sourcePath The path of the source file.
     * @return The message.
     */
    public static String openSourceFileFail(String sourcePath) {
        return "Failed to open source file: " + sourcePath;
    }

    /**
     * Represents the message for when the source file could not be closed successfully.
     *
     * @param sourcePath The path of the source file.
     * @return The message.
     */
    public static String closeSourceFileFail(String sourcePath) {
        return "Failed to close source file: " + sourcePath;
    }

    /**
     * Represents the message for when reading of the source file fails.
     *
     * @param sourcePath The path of the source file.
     * @return The message.
     */
    public static String readSourceFileFail(String sourcePath) {
        return "Failed to read source file: " + sourcePath;
    }

}
