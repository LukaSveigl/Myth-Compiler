package org.mythc.compiler.data.lexicalAnalysis;

import org.mythc.compiler.common.logging.Location;

/**
 * The {@code Symbol} class represents a symbol in the source code. A symbol consists of a lexeme, a token, and a
 * location in the source code.
 */
public class Symbol {

    /** The lexeme of the symbol. */
    public final String lexeme;

    /** The token of the symbol. */
    public final Token token;

    /** The location of the symbol. */
    public final Location location;

    /**
     * Constructs a new {@code Symbol}.
     *
     * @param lexeme   The lexeme of the symbol.
     * @param token    The token of the symbol.
     * @param location The location of the symbol.
     */
    public Symbol(String lexeme, Token token, Location location) {
        this.lexeme = lexeme;
        this.token = token;
        this.location = location;
    }

}
