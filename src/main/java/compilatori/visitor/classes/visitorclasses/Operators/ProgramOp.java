package compilatori.visitor.classes.visitorclasses.Operators;

import compilatori.visitor.classes.visitorclasses.baseclasses.*;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;


import java.util.ArrayList;
import java.util.List;

public class ProgramOp extends Node implements Scopable {
    private DeclsOp decls;
    private BeginEndOp beginEndOp;
    private SymbolTable symbolTable;

    public ProgramOp(DeclsOp decls, BeginEndOp beginEndOp) {
        this.decls = decls;
        this.beginEndOp = beginEndOp;
        this.symbolTable = new SymbolTable();
    }

    public ProgramOp() {
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
