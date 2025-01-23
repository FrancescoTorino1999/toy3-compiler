package compilatori.visitor.classes.visitorclasses.Expressions;

import compilatori.visitor.classes.visitorclasses.Operators.ExprOp;
import compilatori.visitor.classes.visitorclasses.baseclasses.TypeEnum;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FunCall extends ExprOp implements Visitable {
    private Identifier identifier;
    private ArrayList<Visitable> exprOps;
    private TypeEnum type;
    private boolean isFunction;
    private boolean isProcedure;
    private LinkedHashMap<String, TypeEnum> inputParameters;

    public FunCall(Identifier identifier, ArrayList<Visitable> exprOps) {
        this.identifier = identifier;
        this.exprOps = exprOps;
    }

    public FunCall() {
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public ArrayList<Visitable> getExprOps() {
        return exprOps;
    }

    public void setExprOps(ArrayList<Visitable> exprOps) {
        this.exprOps = exprOps;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();

        // Aggiungi l'identificatore come primo figlio, se presente
        if (identifier != null) {
            children.add(identifier);
        }

        // Aggiungi tutti gli elementi di exprOps come figli, se presenti
        if (exprOps != null && !exprOps.isEmpty()) {
            children.addAll(exprOps);
        }

        return children;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public boolean isFunction() {
        return isFunction;
    }

    public void setFunction(boolean function) {
        isFunction = function;
    }

    public boolean isProcedure() {
        return isProcedure;
    }

    public void setProcedure(boolean procedure) {
        isProcedure = procedure;
    }

    public Map<String, TypeEnum> getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(LinkedHashMap<String, TypeEnum> inputParameters) {
        this.inputParameters = inputParameters;
    }
}
