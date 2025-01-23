package compilatori.visitor.classes.visitorclasses.Statements;

import compilatori.visitor.classes.visitorclasses.Operators.BeginEndOp;
import compilatori.visitor.classes.visitorclasses.Operators.BodyOp;
import compilatori.visitor.classes.visitorclasses.Operators.DeclsOp;
import compilatori.visitor.classes.visitorclasses.Operators.StatOp;
import compilatori.visitor.classes.visitorclasses.baseclasses.SymbolTable;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;


public class LetOp extends StatOp {
    private DeclsOp decls;
    private BeginEndOp beginEndOp;
    private SymbolTable symbolTable;

    public LetOp(DeclsOp decls, BeginEndOp beginEndOp) {
        this.decls = decls;
        this.beginEndOp = beginEndOp;
        this.symbolTable = new SymbolTable();
    }

    public LetOp() {
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();
        if (decls != null) {
            children.add(decls);
        }
        if (beginEndOp != null) {
            children.add(beginEndOp);
        }
        return children;
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public DeclsOp getDecls() {
        return decls;
    }

    public void setDecls(DeclsOp decls) {
        this.decls = decls;
    }

    public BeginEndOp getBeginEndOp() {
        return beginEndOp;
    }

    public void setBeginEndOp(BeginEndOp beginEndOp) {
        this.beginEndOp = beginEndOp;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
