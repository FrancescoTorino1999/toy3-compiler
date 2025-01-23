package compilatori.visitor.classes.visitorclasses.Operators;

import compilatori.visitor.classes.visitorclasses.Expressions.TypeOrConstant;
import compilatori.visitor.classes.visitorclasses.baseclasses.Node;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;


import java.util.ArrayList;
import java.util.List;

public class VarsOptInitOp extends Node {
    private ArrayList<VarOpInitOp> varsOptInitOp;
    private TypeOrConstant constant;

    public VarsOptInitOp(ArrayList<VarOpInitOp> varsOptInitOp, TypeOrConstant constant) {
        this.varsOptInitOp = varsOptInitOp;
        this.constant = constant;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();
        if (varsOptInitOp != null) {
            children.addAll(varsOptInitOp); // Aggiungi tutti i VarsOptInitOp figli
        }
        if (constant != null) {
            children.add(constant); // Aggiungi la costante come figlio
        }
        return children;
    }

    public ArrayList<VarOpInitOp> getVarsOptInitOp() {
        return varsOptInitOp;
    }

    public void setVarsOptInitOp(ArrayList<VarOpInitOp> varsOptInitOp) {
        this.varsOptInitOp = varsOptInitOp;
    }

    public TypeOrConstant getConstant() {
        return constant;
    }

    public void setConstant(TypeOrConstant constant) {
        this.constant = constant;
    }
}
