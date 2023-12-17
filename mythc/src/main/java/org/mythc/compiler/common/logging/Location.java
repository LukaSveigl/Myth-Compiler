package org.mythc.compiler.common.logging;

/**
 * Information about a location of a part of the source file.
 */
public class Location {

    /** The line number of the first character of the specified part of the source file. */
    public final int startLine;
    /** The column number of the first character of the specified part of the source file. */
    public final int startColumn;
    /** The line number of the last character of the specified part of the source file. */
    public final int endLine;
    /** The column number of the last character of the specified part of the source file. */
    public final int endColumn;

    /**
     * Constructs a new location with the full range of the specified location.
     *
     * @param startLine   The line number of the first character of the specified part of the source file.
     * @param startColumn The column number of the first character of the specified part of the source file.
     * @param endLine     The line number of the last character of the specified part of the source file.
     * @param endColumn   The column number of the last character of the specified part of the source file.
     */
    public Location(int startLine, int startColumn, int endLine, int endColumn) {
        this.startLine = startLine;
        this.startColumn = startColumn;
        this.endLine = endLine;
        this.endColumn = endColumn;
    }

    /**
     * Constructs a new location of a single character.
     *
     * @param line   The line number of the character.
     * @param column The column number of the character.
     */
    public Location(int line, int column) {
        this(line, column, line, column);
    }

    /**
     * Constructs a new location based on some other location.
     *
     * @param location The location to copy.
     */
    public Location(Location location) {
        this(location.startLine, location.startColumn, location.endLine, location.endColumn);
    }

    /**
     * Constructs a new empty location.
     */
    public Location() {
        this(0, 0, 0, 0);
    }

    @Override
    public String toString() {
        return String.format("%d:%d-%d:%d", startLine, startColumn, endLine, endColumn);
    }

}
