package de.jflex.analizzatore.lessicale;
import java_cup.runtime.*;

%%
%class Lexer
%unicode
%cup
%line
%column
%state STRING
%state COMMENT
%state CHAR
%{
  private StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

/* Definizione delle espressioni regolari */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

/* Commenti */
Comment = {TraditionalComment} | {EndOfLineComment}
TraditionalComment = "/*" [^*]* "*"+ "/"
EndOfLineComment = "//" [^\n]*

/* Identificatori e numeri */
Identifier = [a-zA-Z_][a-zA-Z_0-9]*
digit = [0-9]
digitWithoutZero = [1-9]
digits = {digit}*
optionalFraction = "." {digits}
optionalExp = [eE] ("-" | "+")? {digits}
SingleCharacter = [a-zA-Z_0-9]

IntegerLiteral = [-]? {digitWithoutZero}* | {digits}
DoubleLiteral = [-]? (({digitWithoutZero} {digit}*) | 0 ) {optionalFraction}? {optionalExp}?

%%
/* Parole chiave */
<YYINITIAL> "program"   { return symbol(sym.PROGRAM); }
<YYINITIAL> "begin"     { return symbol(sym.BEGIN); }
<YYINITIAL> "end"       { return symbol(sym.END); }
<YYINITIAL> "def"       { return symbol(sym.DEF); }
<YYINITIAL> "ref"       { return symbol(sym.REF); }
<YYINITIAL> "if"        { return symbol(sym.IF); }
<YYINITIAL> "then"      { return symbol(sym.THEN); }
<YYINITIAL> "else"      { return symbol(sym.ELSE); }
<YYINITIAL> "while"     { return symbol(sym.WHILE); }
<YYINITIAL> "do"        { return symbol(sym.DO); }
<YYINITIAL> "return"    { return symbol(sym.RETURN); }
<YYINITIAL> "true"      { return symbol(sym.TRUE, Boolean.TRUE); }
<YYINITIAL> "false"     { return symbol(sym.FALSE, Boolean.FALSE); }
<YYINITIAL> "int"       { return symbol(sym.INT); }
<YYINITIAL> "double"    { return symbol(sym.DOUBLE); }
<YYINITIAL> "bool"      { return symbol(sym.BOOL); }
<YYINITIAL> "char"      { return symbol(sym.CHAR); }
<YYINITIAL> "let"      { return symbol(sym.LET); }
<YYINITIAL> "in"      { return symbol(sym.INLET); }
<YYINITIAL> "end let"      { return symbol(sym.ENDLET); }
<YYINITIAL> "string"      { return symbol(sym.STRING); }

/* Simboli */
<YYINITIAL> "<<"        { return symbol(sym.IN); }  // Input da tastiera
<YYINITIAL> ">>"        { return symbol(sym.OUT); } // Output a schermo
<YYINITIAL> "!>>"       { return symbol(sym.OUTNL); } // Output a schermo con newline
<YYINITIAL> ":"         { return symbol(sym.COLON); }
<YYINITIAL> ";"         { yybegin(YYINITIAL); return symbol(sym.SEMI); }
<YYINITIAL> ","         { return symbol(sym.COMMA); }
<YYINITIAL> "&"         { return symbol(sym.AMPERSAND); }
<YYINITIAL> "="         { return symbol(sym.ASSIGNDECL); }
<YYINITIAL> "|"         { return symbol(sym.PIPE); }
<YYINITIAL> "("         { return symbol(sym.LPAR); }
<YYINITIAL> ")"         { return symbol(sym.RPAR); }
<YYINITIAL> "{"         { return symbol(sym.LBRAC); }
<YYINITIAL> "}"         { return symbol(sym.RBRAC); }
<YYINITIAL> "+"         { return symbol(sym.PLUS); }
<YYINITIAL> "-"         { return symbol(sym.MINUS); }
<YYINITIAL> "/"         { return symbol(sym.DIV); }
<YYINITIAL> "=="         { return symbol(sym.EQ); }
<YYINITIAL> "<"         { return symbol(sym.LT); }
<YYINITIAL> ">"         { return symbol(sym.GT); }
<YYINITIAL> "<="        { return symbol(sym.LE); }
<YYINITIAL> ">="        { return symbol(sym.GE); }
<YYINITIAL> "<>"        { return symbol(sym.NE); }
<YYINITIAL> "and"       { return symbol(sym.AND); }
<YYINITIAL> "or"        { return symbol(sym.OR); }
<YYINITIAL> "not"       { return symbol(sym.NOT); }
<YYINITIAL> ":="        { return symbol(sym.ASSIGN); }

/* Simbolo asterisco */
<YYINITIAL> "*"         { return symbol(sym.TIMES); } // Moltiplicazione

/* Identificatori */
<YYINITIAL> {Identifier} { return symbol(sym.ID, yytext()); }

/* Numeri */
<YYINITIAL> {IntegerLiteral} { return symbol(sym.INT_CONST, Integer.parseInt(yytext())); }
<YYINITIAL> {DoubleLiteral} { return symbol(sym.DOUBLE_CONST, Double.parseDouble(yytext())); }

<YYINITIAL> "\"" { yybegin(STRING); string.setLength(0);}  // Inizia a leggere una stringa

<STRING>  \" { yybegin(YYINITIAL); return symbol(sym.STRING_CONST, "\""+string.toString()+"\""); }
<STRING>  [^\n\r\"\\]+ { string.append(yytext()); }
<STRING>  \\t { string.append('\t'); }
<STRING>  \\r { string.append('\r'); }
<STRING>  \\\" { string.append('\"'); }
<STRING>  \\ { string.append('\\'); }
<STRING>  <<EOF>> { throw new Error("Stringa non chiusa alla linea " + yyline + ", colonna " + yycolumn); }

\'                             { yybegin(CHAR); }

<CHAR> {
  {SingleCharacter}\'            { yybegin(YYINITIAL); return symbol(sym.CHAR_CONST, String.valueOf(yytext().charAt(0))); }

  /* escape sequences */
  "\\b"\'                        { yybegin(YYINITIAL); return symbol(sym.CHAR_CONST, '\b');}
  "\\t"\'                        { yybegin(YYINITIAL); return symbol(sym.CHAR_CONST, '\t');}
  "\\n"\'                        { yybegin(YYINITIAL); return symbol(sym.CHAR_CONST, '\n');}
  "\\f"\'                        { yybegin(YYINITIAL); return symbol(sym.CHAR_CONST, '\f');}
  "\\r"\'                        { yybegin(YYINITIAL); return symbol(sym.CHAR_CONST, '\r');}
  "\\\""\'                       { yybegin(YYINITIAL); return symbol(sym.CHAR_CONST, '\"');}
  "\\'"\'                        { yybegin(YYINITIAL); return symbol(sym.CHAR_CONST, '\'');}
  "\\\\"\'                       { yybegin(YYINITIAL); return symbol(sym.CHAR_CONST, '\\'); }

  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { throw new RuntimeException("Unterminated character literal at end of line"); }
}


<YYINITIAL> "/*" { yybegin(COMMENT); }

<COMMENT> "*/" { yybegin(YYINITIAL); }

<COMMENT> {LineTerminator} { /* Ignora ma aggiorna posizione */ }

<COMMENT> . { /* Ignora */ }

<COMMENT> <<EOF>> {
  throw new Error("Commento non chiuso alla linea " + yyline + ", colonna " + yycolumn);
}

/* Commenti */
<YYINITIAL> {Comment} { /* ignora i commenti */ }

/* Spazi bianchi */
<YYINITIAL> {WhiteSpace} { /* ignora gli spazi bianchi */ }

/* Gestione degli errori */
<YYINITIAL> . { throw new Error("Carattere illegale: <" + yytext() + "> alla linea " + yyline + ", colonna " + yycolumn); }
<STRING> . { throw new Error("Carattere illegale in stringa <" + yytext() + "> alla linea " + yyline + ", colonna " + yycolumn); }
