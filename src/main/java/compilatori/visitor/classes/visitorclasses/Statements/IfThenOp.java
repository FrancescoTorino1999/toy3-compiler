package compilatori.visitor.classes.visitorclasses.Statements;

import compilatori.visitor.classes.visitorclasses.Operators.BodyOp;
import compilatori.visitor.classes.visitorclasses.Operators.StatOp;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;
import java.util.ArrayList;
import java.util.List;


public class IfThenOp extends StatOp {
    private Visitable exprOp;
    private BodyOp thenBody;

    public IfThenOp(Visitable exprOp, BodyOp thenBody) {
        this.exprOp = exprOp;
        this.thenBody = thenBody;
    }

    public IfThenOp() {
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();

        // Aggiungi i figli solo se non sono null
        if (exprOp != null) {
            children.add(exprOp);
        }
        if (thenBody != null) {
            children.add(thenBody);
        }

        return children;
    }

    public Visitable getExprOp() {
        return exprOp;
    }

    public void setExprOp(Visitable exprOp) {
        this.exprOp = exprOp;
    }

    public BodyOp getThenBody() {
        return thenBody;
    }

    public void setThenBody(BodyOp thenBody) {
        this.thenBody = thenBody;
    }
}
