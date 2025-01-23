package compilatori.visitor.classes.visitorclasses.Statements;

import compilatori.visitor.classes.visitorclasses.Expressions.FunCall;
import compilatori.visitor.classes.visitorclasses.Operators.StatOp;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;


public class FunCallOp extends StatOp {
    private FunCall funcall;

    public FunCallOp(FunCall funcall) {
        this.funcall = funcall;
    }

    public FunCallOp() {
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();

        // Aggiungi funcall come figlio
        if (funcall != null) {
            children.add(funcall);
        }

        return children;
    }

    public FunCall getFuncall() {
        return funcall;
    }

    public void setFuncall(FunCall funcall) {
        this.funcall = funcall;
    }
}
