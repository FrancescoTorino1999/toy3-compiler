package compilatori.visitor.classes.visitorclasses.Expressions;

import compilatori.visitor.classes.visitorclasses.Operators.ExprOp;
import compilatori.visitor.classes.visitorclasses.baseclasses.TypeEnum;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

public class UnaryOp extends ExprOp {
    private String name;
    private Visitable expr1;

    public UnaryOp(Mode mode, TypeEnum tipo, String name, Visitable expr1) {
        super(mode, tipo);
        this.name = name;
        this.expr1 = expr1;
    }

    public UnaryOp(String name, Visitable expr1) {
        this.name = name;
        this.expr1 = expr1;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Visitable getExpr1() {
        return expr1;
    }

    public void setExpr1(Visitable expr1) {
        this.expr1 = expr1;
    }
}
