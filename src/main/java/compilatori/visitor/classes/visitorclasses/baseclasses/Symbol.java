package compilatori.visitor.classes.visitorclasses.baseclasses;

public class Symbol {

    private String symbol;

    private Kind kind;

    public TypeSym typeSym;

    public Symbol(String symbol, Kind kind, TypeSym typeSym) {
        this.symbol = symbol;
        this.kind = kind;
        this.typeSym = typeSym;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public TypeSym getTypeSym() {
        return typeSym;
    }

    public void setTypeSym(TypeSym typeSym) {
        this.typeSym = typeSym;
    }

    @Override
    public boolean equals(Object obj) {
        Symbol sym = (Symbol) obj;
        return this.symbol.equals(sym.getSymbol());
    }
}
