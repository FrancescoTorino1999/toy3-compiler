package de.jflex.analizzatore.lessicale;

public class Constants {
    public static final String[] TOKEN_NAMES = {
            "EOF",
            "IF",
            "THEN",
            "ELSE",
            "WHILE",
            "INT",
            "FLOAT",
            "ID",
            "NUMBER",
            "LPAR",
            "RPAR",
            "LBRA",
            "RBRA",
            "COMA",
            "SEMI",
            "ASSIGN",
            "RELOP_GE",
            "RELOP_GT",
            "RELOP_LE",
            "RELOP_LT",
            "RELOP_EQ",
            "RELOP_NE",
            "STRING_LITERAL",
            "ERROR"
    };

    // Metodo per ottenere il nome del token in base al suo numero
    public static String getTokenName(int tokenType) {
        if (tokenType < 0 || tokenType >= TOKEN_NAMES.length) {
            return "UNKNOWN_TOKEN";
        }
        return TOKEN_NAMES[tokenType];
    }
}
