package compilatori.visitor.classes.visitorclasses.Operators;

import compilatori.visitor.classes.visitorclasses.Expressions.Consts.Const;
import compilatori.visitor.classes.visitorclasses.Expressions.Identifier;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.baseclasses.Node;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;


public class VarOpInitOp extends Node {

    private Identifier identifier;
    private Const constant;
    private Visitable exprOp;

    public VarOpInitOp(Identifier identifier, Const constant, Visitable exprOp) {
        this.identifier = identifier;
        this.constant = constant;
        this.exprOp = exprOp;
    }

    public VarOpInitOp() {
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();
        if (identifier != null) {
            children.add(identifier);
        }
        if (constant != null) {
            children.add(constant);
        }
        if (exprOp != null) {
            children.add(exprOp);
        }
        return children;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public Const getConstant() {
        return constant;
    }

    public void setConstant(Const constant) {
        this.constant = constant;
    }

    public Visitable getExprOp() {
        return exprOp;
    }

    public void setExprOp(Visitable exprOp) {
        this.exprOp = exprOp;
    }


}
