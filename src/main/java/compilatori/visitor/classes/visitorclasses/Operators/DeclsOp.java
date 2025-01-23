package compilatori.visitor.classes.visitorclasses.Operators;

import compilatori.visitor.classes.visitorclasses.baseclasses.Node;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;


import java.util.ArrayList;
import java.util.List;


public class DeclsOp extends Node {
    private ArrayList<VarDeclOp> decls;
    private ArrayList<DefDeclOp> defDeclOp;

    public DeclsOp(ArrayList<VarDeclOp> decls, ArrayList<DefDeclOp> defDeclOp) {
        this.decls = decls;
        this.defDeclOp = defDeclOp;
    }

    public DeclsOp() {
    }

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
        if (defDeclOp != null) {
            children.addAll(defDeclOp); // Aggiunge tutte le dichiarazioni di funzioni
        }
        return children;
    }

    public ArrayList<VarDeclOp> getDecls() {
        return decls;
    }

    public void setDecls(ArrayList<VarDeclOp> decls) {
        this.decls = decls;
    }

    public ArrayList<DefDeclOp> getDefDeclOp() {
        return defDeclOp;
    }

    public void setDefDeclOp(ArrayList<DefDeclOp> defDeclOp) {
        this.defDeclOp = defDeclOp;
    }


}
