package compilatori.visitor.classes.visitorclasses.Operators;

import compilatori.visitor.classes.visitorclasses.baseclasses.*;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;


import java.util.ArrayList;
import java.util.List;

public class BodyOp extends Node implements Scopable {
    private ArrayList<VarDeclOp> varDeclOps;
    private ArrayList<StatOp> statOps;
    private SymbolTable symbolTable;

    public BodyOp() {
    }

    public BodyOp(ArrayList<VarDeclOp> varDeclOps, ArrayList<StatOp> statOps) {
        this.varDeclOps = varDeclOps;
        this.statOps = statOps;
        this.symbolTable = new SymbolTable();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();
        if (varDeclOps != null) {
            children.addAll(varDeclOps); // Aggiunge tutte le dichiarazioni di variabili come figli
        }
        if (statOps != null) {
            children.addAll(statOps); // Aggiunge tutte le dichiarazioni di statement come figli
        }
        return children;
    }

    public ArrayList<VarDeclOp> getVarDeclOps() {
        return varDeclOps;
    }

    public void setVarDeclOps(ArrayList<VarDeclOp> varDeclOps) {
        this.varDeclOps = varDeclOps;
    }

    public ArrayList<StatOp> getStatOps() {
        return statOps;
    }

    public void setStatOps(ArrayList<StatOp> statOps) {
        this.statOps = statOps;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
