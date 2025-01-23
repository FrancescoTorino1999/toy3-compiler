package compilatori.visitor.classes.visitorclasses.Statements;

import compilatori.visitor.classes.visitorclasses.Operators.BodyOp;
import compilatori.visitor.classes.visitorclasses.Operators.StatOp;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;


public class WhileOp extends StatOp {
    private Visitable exprOp;
    private BodyOp bodyOp;

    public WhileOp() {
    }

    public WhileOp(Visitable exprOp, BodyOp bodyOp) {
        this.exprOp = exprOp;
        this.bodyOp = bodyOp;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();

        // Aggiungi l'exprOp (condizione) se non è null
        if (exprOp != null) {
            children.add(exprOp);
        }

        // Aggiungi il bodyOp (corpo del ciclo) se non è null
        if (bodyOp != null) {
            children.add(bodyOp);
        }

        return children;
    }

    public Visitable getExprOp() {
        return exprOp;
    }

    public void setExprOp(Visitable exprOp) {
        this.exprOp = exprOp;
    }

    public BodyOp getBodyOp() {
        return bodyOp;
    }

    public void setBodyOp(BodyOp bodyOp) {
        this.bodyOp = bodyOp;
    }
}
