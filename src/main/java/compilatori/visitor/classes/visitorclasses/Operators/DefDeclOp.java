package compilatori.visitor.classes.visitorclasses.Operators;

import compilatori.visitor.classes.visitorclasses.Expressions.Identifier;
import compilatori.visitor.classes.visitorclasses.Expressions.OptType;
import compilatori.visitor.classes.visitorclasses.baseclasses.Node;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;


import java.util.ArrayList;
import java.util.List;

public class DefDeclOp extends Node {
    private Identifier identifier;
    private ArrayList<ParDeclOp> parDeclOps;
    private OptType optType;
    private BodyOp bodyOp;

    public DefDeclOp() {
    }

    public DefDeclOp(Identifier identifier, ArrayList<ParDeclOp> parDeclOps, OptType optType, BodyOp bodyOp) {
        this.identifier = identifier;
        this.parDeclOps = parDeclOps;
        this.optType = optType;
        this.bodyOp = bodyOp;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();
        if (identifier != null) {
            children.add(identifier); // Aggiunge l'identificatore come figlio
        }
        if (parDeclOps != null) {
            children.addAll(parDeclOps); // Aggiunge tutti i parametri come figli
        }
        if (optType != null) {
            children.add(optType); // Aggiunge l'OptType come figlio
        }
        if (bodyOp != null) {
            children.add(bodyOp); // Aggiunge il BodyOp come figlio
        }
        return children;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public ArrayList<ParDeclOp> getParDeclOps() {
        return parDeclOps;
    }

    public void setParDeclOps(ArrayList<ParDeclOp> parDeclOps) {
        this.parDeclOps = parDeclOps;
    }

    public OptType getOptType() {
        return optType;
    }

    public void setOptType(OptType optType) {
        this.optType = optType;
    }

    public BodyOp getBodyOp() {
        return bodyOp;
    }

    public void setBodyOp(BodyOp bodyOp) {
        this.bodyOp = bodyOp;
    }
}
