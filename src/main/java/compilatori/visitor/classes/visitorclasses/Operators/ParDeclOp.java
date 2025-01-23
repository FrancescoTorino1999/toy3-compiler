package compilatori.visitor.classes.visitorclasses.Operators;

import compilatori.visitor.classes.visitorclasses.Expressions.Type;
import compilatori.visitor.classes.visitorclasses.baseclasses.Node;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;

public class ParDeclOp extends Node {

    private ArrayList<PVarOp> pVarOp;
    private Type type;

    public ParDeclOp(ArrayList<PVarOp> pVarOp, Type type) {
        this.pVarOp = pVarOp;
        this.type = type;
    }

    public ParDeclOp() {
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();
        if (pVarOp != null) {
            children.addAll(pVarOp);
        }
        if (type != null) {
            children.add(type);
        }
        return children;
    }

    public ArrayList<PVarOp> getpVarOp() {
        return pVarOp;
    }

    public void setpVarOp(ArrayList<PVarOp> pVarOp) {
        this.pVarOp = pVarOp;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
