package compilatori.visitor.classes.visitorclasses.Operators;

import compilatori.visitor.classes.visitorclasses.Expressions.Identifier;
import compilatori.visitor.classes.visitorclasses.Expressions.Reference;
import compilatori.visitor.classes.visitorclasses.baseclasses.Node;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;


import java.util.ArrayList;
import java.util.List;


public class PVarOp extends Node {

    private Identifier identifier;
    private Reference isRef;

    public PVarOp() {
    }

    public PVarOp(Identifier identifier, Reference isRef) {
        this.identifier = identifier;
        this.isRef = isRef;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();
        if (identifier != null) {
            children.add(identifier); // Aggiungi il figlio identifier se non nullo
        }
        return children; // isRef non viene aggiunto perché è un valore booleano, non un nodo
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public Reference getIsRef() {
        return isRef;
    }

    public void setIsRef(Reference isRef) {
        this.isRef = isRef;
    }

}
