package compilatori.visitor.classes.visitorclasses.Expressions.Arith;

import compilatori.visitor.classes.visitorclasses.Operators.ExprOp;
import compilatori.visitor.classes.visitorclasses.baseclasses.TypeEnum;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;

public class UminusOp  extends ExprOp  implements Visitable {
    private String name;
    private Visitable expr1;
    private TypeEnum type;

    public UminusOp(Mode mode, TypeEnum tipo, String name, Visitable expr1, TypeEnum type) {
        super(mode, tipo);
        this.name = name;
        this.expr1 = expr1;
        this.type = type;
    }

    public UminusOp(String name, Visitable expr1, TypeEnum type) {
        this.name = name;
        this.expr1 = expr1;
        this.type = type;
    }

    public UminusOp(String name, Visitable expr1) {
        this.name = name;
        this.expr1 = expr1;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();
        if (expr1 != null) {
            children.add(expr1); // Aggiungi il primo operando
        }
        return children;
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

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }
}
