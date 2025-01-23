package compilatori.visitor.classes.visitorclasses.Operators;

import compilatori.visitor.classes.visitorclasses.baseclasses.*;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;


import java.util.ArrayList;
import java.util.List;

public class BeginEndOp extends Node  implements Scopable {
    private ArrayList<VarDeclOp> decls;
    private ArrayList<StatOp> statOps;
    private SymbolTable symbolTable;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();
        if (decls != null) {
            children.addAll(decls); // Aggiunge tutte le dichiarazioni di variabili
        }
        if (statOps != null) {
            children.addAll(statOps); // Aggiunge tutte le istruzioni
        }


        return children;
    }

    public BeginEndOp(ArrayList<VarDeclOp> decls, ArrayList<StatOp> statOps) {
        this.decls = decls;
        this.statOps = statOps;
        this.symbolTable = new SymbolTable();
    }

    public ArrayList<VarDeclOp> getDecls() {
        return decls;
    }

    public void setDecls(ArrayList<VarDeclOp> decls) {
        this.decls = decls;
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
