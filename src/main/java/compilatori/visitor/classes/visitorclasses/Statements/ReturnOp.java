package compilatori.visitor.classes.visitorclasses.Statements;

import compilatori.visitor.classes.visitorclasses.Operators.StatOp;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;

public class ReturnOp extends StatOp {
    private Visitable exprOp;


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public ReturnOp(Visitable exprOp) {
        this.exprOp = exprOp;
    }

    public ReturnOp() {
    }

    public Visitable getExprOp() {
        return exprOp;
    }

    public void setExprOp(Visitable exprOp) {
        this.exprOp = exprOp;
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();

        // Aggiungi l'expression operation (exprOp) se non è null
        if (exprOp != null) {
            children.add(exprOp);
        }

        return children;
    }
}
