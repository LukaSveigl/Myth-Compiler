package org.mythc.compiler.phases.lexicalAnalysis;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.mythc.compiler.common.logging.CompilerLogger;
import org.mythc.compiler.common.logging.Location;
import org.mythc.compiler.data.lexicalAnalysis.Token;
import org.mythc.compiler.data.lexicalAnalysis.Symbol;

public class LexicalAnalyser implements AutoCloseable {

    private final String sourcePath;

    private final FileReader fileReader;

    private static class CurrentState {
        private static int startLine = 1;
        private static int startColumn = 1;
        private static int endLine = 1;
        private static int endColumn = 1;

        private static String lexeme;

        private static boolean inInlineComment;
        private static boolean inBlockComment;
        private static boolean inString;
    }

    public LexicalAnalyser(String sourcePath) {
        this.sourcePath = sourcePath;
        try {
            this.fileReader = new FileReader(new File(sourcePath));
        } catch (Exception e) {
            CompilerLogger.internalError("Failed to open source file: " + sourcePath);
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            this.fileReader.close();
        } catch (Exception e) {
            CompilerLogger.internalError("Failed to close source file: " + sourcePath);
            throw new RuntimeException(e);
        }
    }

    public Symbol next() {
        try {
            while (this.fileReader.ready()) {
                char character = (char) this.fileReader.read();

                updateCurrentStateFlags(character);

                updateCurrentStateLocation(character);

                if (isWhitespace(character)) {
                    continue;
                }

                CurrentState.lexeme += character;

                Symbol symbol = assembleSymbol();
                if (symbol != null) {
                    return symbol;
                }
            }

            // TODO: Handle EOF
            return null;
        } catch (IOException e) {
            CompilerLogger.internalError("Failed to read source file: " + sourcePath);
            throw new RuntimeException(e);
        }
    }

    private void updateCurrentStateFlags(char character) {
        if (character == '\\' && CurrentState.lexeme.charAt(CurrentState.lexeme.length() - 1) == '\\') {
            CurrentState.inInlineComment = true;
        } else if (character == '*' && CurrentState.lexeme.charAt(CurrentState.lexeme.length() - 1) == '\\') {
            CurrentState.inBlockComment = true;
        } else if (character == '\n') {
            CurrentState.inInlineComment = false;
            CurrentState.lexeme = "";
        } else if (character == '/' && CurrentState.lexeme.charAt(CurrentState.lexeme.length() - 1) == '*') {
            CurrentState.inBlockComment = false;
            CurrentState.lexeme = "";
        } else if (character == '"' && CurrentState.lexeme.charAt(CurrentState.lexeme.length() - 1) != '\\') {
            CurrentState.inString = !CurrentState.inString;
        }
    }

    private void updateCurrentStateLocation(char character) {
        if (character == '\n') {
            CurrentState.endLine++;
        } else if (character == '\r') {
            CurrentState.endColumn = 0;
        } else if (character == '\t') {
            CurrentState.endColumn += 4 - (CurrentState.endColumn % 4);
        } else {
            CurrentState.endColumn++;
        }
    }

    private boolean isWhitespace(char character) {
        return character == ' ' || character == '\t' || character == '\n' || character == '\r';
    }

    private Symbol assembleSymbol() {
        if (CurrentState.inInlineComment || CurrentState.inBlockComment || CurrentState.inString) {
            return null;
        }
        Token previousToken = matchLexeme(CurrentState.lexeme.substring(0, CurrentState.lexeme.length() - 1));
        Token currentToken = matchLexeme(CurrentState.lexeme);
        if (previousToken != Token.UNKNOWN && currentToken == Token.UNKNOWN) {
            return new Symbol(CurrentState.lexeme.substring(0, CurrentState.lexeme.length() - 1), previousToken,
                    new Location(CurrentState.startLine, CurrentState.startColumn, CurrentState.endLine,
                            CurrentState.endColumn));
        }
        return null;
    }

    private Token matchLexeme(String lexeme) {
        switch (lexeme) {
            case "import": return Token.KW_IMPORT;
            // Declarations.
            case "let": return Token.KW_LET;
            case "fun": return Token.KW_FUN;
            case "typ": return Token.KW_TYP;
            case "struct": return Token.KW_STRUCT;
            case "enum": return Token.KW_ENUM;
            case "class": return Token.KW_CLASS;
            // Modifiers.
            case "public": return Token.KW_PUBLIC;
            case "private": return Token.KW_PRIVATE;
            case "protected": return Token.KW_PROTECTED;
            case "mut": return Token.KW_MUT;
            case "static": return Token.KW_STATIC;
            // Expressions.
            case "and": return Token.KW_AND;
            case "or": return Token.KW_OR;
            case "not": return Token.KW_NOT;
            case "if": return Token.KW_IF;
            case "else": return Token.KW_ELSE;
            case "match": return Token.KW_MATCH;
            case "case": return Token.KW_CASE;
            case "while": return Token.KW_WHILE;
            case "do": return Token.KW_DO;
            case "for": return Token.KW_FOR;
            case "in": return Token.KW_IN;
            case "return": return Token.KW_RETURN;
            case "break": return Token.KW_BREAK;
            case "continue": return Token.KW_CONTINUE;
            case "new": return Token.KW_NEW;
            case "del": return Token.KW_DEL;
            // Types.
            case "void": return Token.KW_VOID;
            case "bool": return Token.KW_BOOL;
            case "char": return Token.KW_CHAR;
            case "uint8": return Token.KW_UINT8;
            case "uint16": return Token.KW_UINT16;
            case "uint32": return Token.KW_UINT32;
            case "uint64": return Token.KW_UINT64;
            case "int8": return Token.KW_INT8;
            case "int16": return Token.KW_INT16;
            case "int32": return Token.KW_INT32;
            case "int64": return Token.KW_INT64;
            case "float32": return Token.KW_FLOAT32;
            case "float64": return Token.KW_FLOAT64;
            case "str": return Token.KW_STR;
            // Symbols.
            case "(": return Token.SYM_LPAREN;
            case ")": return Token.SYM_RPAREN;
            case "{": return Token.SYM_LBRACE;
            case "}": return Token.SYM_RBRACE;
            case "[": return Token.SYM_LBRACKET;
            case "]": return Token.SYM_RBRACKET;
            case ",": return Token.SYM_COMMA;
            case ".": return Token.SYM_DOT;
            case ":": return Token.SYM_COLON;
            case ";": return Token.SYM_SEMICOLON;
            case "+": return Token.SYM_PLUS;
            case "-": return Token.SYM_MINUS;
            case "*": return Token.SYM_STAR;
            case "/": return Token.SYM_SLASH;
            case "%": return Token.SYM_PERCENT;
            case "!": return Token.SYM_EXCLAMATION;
            case "?": return Token.SYM_QUESTION;
            case "++": return Token.SYM_INCREMENT;
            case "--": return Token.SYM_DECREMENT;
            case "+=": return Token.SYM_PLUS_ASSIGN;
            case "-=": return Token.SYM_MINUS_ASSIGN;
            case "*=": return Token.SYM_STAR_ASSIGN;
            case "/=": return Token.SYM_SLASH_ASSIGN;
            case "%=": return Token.SYM_PERCENT_ASSIGN;
            case "==": return Token.SYM_EQUAL;
            case "!=": return Token.SYM_NOT_EQUAL;
            case "<": return Token.SYM_LESS;
            case "<=": return Token.SYM_LESS_EQUAL;
            case ">": return Token.SYM_GREATER;
            case ">=": return Token.SYM_GREATER_EQUAL;
            case "@": return Token.SYM_REFERENCE;
            case "$": return Token.SYM_DEREFERENCE;
            case "=": return Token.SYM_ASSIGN;
            case "&": return Token.SYM_BITWISE_AND;
            case "|": return Token.SYM_BITWISE_OR;
            case "^": return Token.SYM_BITWISE_XOR;
            case "~": return Token.SYM_BITWISE_NOT;
            case "&=": return Token.SYM_BITWISE_AND_ASSIGN;
            case "|=": return Token.SYM_BITWISE_OR_ASSIGN;
            case "^=": return Token.SYM_BITWISE_XOR_ASSIGN;
            case "~=": return Token.SYM_BITWISE_NOT_ASSIGN;
            case "=>": return Token.SYM_FAT_ARROW;
            case "->": return Token.SYM_THIN_ARROW;
            // Literals and identifiers.
            case "true": return Token.LIT_TRUE;
            case "false": return Token.LIT_FALSE;
            case "null": return Token.LIT_NULL;
            default: {
                Token token = matchString(lexeme);
                if (token != null)
                    return token;
                token = matchFloat(lexeme);
                if (token != null)
                    return token;
                token = matchInt(lexeme);
                if (token != null)
                    return token;
                token = matchChar(lexeme);
                if (token != null)
                    return token;
                token = matchIdentifier(lexeme);
                if (token != null)
                    return token;
                return Token.UNKNOWN;
            }
        }
    }

    private Token matchString(String lexeme) {
        if (lexeme.charAt(0) == '"' && lexeme.charAt(lexeme.length() - 1) == '"') {
            return Token.LIT_STR;
        }
        return null;
    }

    private Token matchFloat(String lexeme) {
        if (lexeme.contains(".")) {
            try {
                Float.parseFloat(lexeme);
                return Token.LIT_FLOAT;
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private Token matchInt(String lexeme) {
        try {
            Integer.parseInt(lexeme);
            return Token.LIT_INT;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Token matchChar(String lexeme) {
        if (lexeme.charAt(0) == '\'' && lexeme.charAt(lexeme.length() - 1) == '\'') {
            return Token.LIT_CHAR;
        }
        return null;
    }

    private Token matchIdentifier(String lexeme) {
        if (lexeme.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            return Token.IDENTIFIER;
        }
        return null;
    }

}
