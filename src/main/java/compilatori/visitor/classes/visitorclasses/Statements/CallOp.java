package compilatori.visitor.classes.visitorclasses.Statements;

import compilatori.visitor.classes.visitorclasses.Expressions.Identifier;
import compilatori.visitor.classes.visitorclasses.Operators.ExprOp;
import compilatori.visitor.classes.visitorclasses.Operators.StatOp;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;

public class CallOp extends StatOp {
    private Identifier identifier;
    private ArrayList<ExprOp> exprOps;

    public CallOp(Identifier identifier, ArrayList<ExprOp> exprOps) {
        this.identifier = identifier;
        this.exprOps = exprOps;
    }

    public CallOp() {
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();

        // Aggiungi l'identifier come figlio
        if (identifier != null) {
            children.add(identifier);
        }

        // Aggiungi tutte le exprOps come figli
        if (exprOps != null) {
            children.addAll(exprOps);
        }

        return children;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public ArrayList<ExprOp> getExprOps() {
        return exprOps;
    }

    public void setExprOps(ArrayList<ExprOp> exprOps) {
        this.exprOps = exprOps;
    }
}
