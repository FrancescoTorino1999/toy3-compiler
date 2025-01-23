package compilatori.visitor.classes.visitorclasses.Operators;

import compilatori.visitor.classes.visitorclasses.baseclasses.Node;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;


public class VarDeclOp extends Node {
    private VarsOptInitOp varsOptInitOp;

    public VarDeclOp() {
    }

    public VarDeclOp(VarsOptInitOp varsOptInitOp) {
        this.varsOptInitOp = varsOptInitOp;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();
        if (varsOptInitOp != null) {
            children.add(varsOptInitOp); // Aggiunge tutti i nodi di inizializzazione delle variabili
        }


        return children;
    }

    public VarsOptInitOp getVarsOptInitOp() {
        return varsOptInitOp;
    }

    public void setVarsOptInitOp(VarsOptInitOp varsOptInitOp) {
        this.varsOptInitOp = varsOptInitOp;
    }



}
