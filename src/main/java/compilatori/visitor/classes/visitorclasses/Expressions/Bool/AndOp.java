package compilatori.visitor.classes.visitorclasses.Expressions.Bool;

import compilatori.visitor.classes.visitorclasses.Operators.ExprOp;
import compilatori.visitor.classes.visitorclasses.baseclasses.TypeEnum;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;

public class AndOp extends ExprOp implements Visitable {
    private String name; //indica che espressione binaria è: plus,...
    private Visitable expr1, expr2;
    private TypeEnum type;

    public AndOp(Mode mode, TypeEnum tipo, String name, Visitable expr1, Visitable expr2, TypeEnum type) {
        super(mode, tipo);
        this.name = name;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.type = type;
    }

    public AndOp(String name, Visitable expr1, Visitable expr2, TypeEnum type) {
        this.name = name;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.type = type;
    }

    public AndOp(String name, Visitable expr1, Visitable expr2) {
        this.name = name;
        this.expr1 = expr1;
        this.expr2 = expr2;
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

    public Visitable getExpr2() {
        return expr2;
    }

    public void setExpr2(Visitable expr2) {
        this.expr2 = expr2;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();
        if (expr1 != null) {
            children.add(expr1); // Aggiungi il primo operando
        }
        if (expr2 != null) {
            children.add(expr2); // Aggiungi il secondo operando
        }
        return children;
    }
}
