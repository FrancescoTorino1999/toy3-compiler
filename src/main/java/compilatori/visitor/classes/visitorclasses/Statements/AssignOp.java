package compilatori.visitor.classes.visitorclasses.Statements;

import compilatori.visitor.classes.visitorclasses.Expressions.Identifier;
import compilatori.visitor.classes.visitorclasses.Operators.StatOp;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;


import java.util.ArrayList;
import java.util.List;


public class AssignOp extends StatOp {
    private ArrayList<Identifier> identifiers;
    private ArrayList<Visitable> exprOps;

    public AssignOp(ArrayList<Identifier> identifiers, ArrayList<Visitable> exprOps) {
        this.identifiers = identifiers;
        this.exprOps = exprOps;
    }

    public AssignOp() {
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();

        // Aggiungi tutti gli identifiers come figli
        if (identifiers != null) {
            children.addAll(identifiers);
        }

        // Aggiungi tutti gli exprOps come figli
        if (exprOps != null) {
            children.addAll(exprOps);
        }

        return children;
    }

    public ArrayList<Identifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(ArrayList<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

    public ArrayList<Visitable> getExprOps() {
        return exprOps;
    }

    public void setExprOps(ArrayList<Visitable> exprOps) {
        this.exprOps = exprOps;
    }
}
