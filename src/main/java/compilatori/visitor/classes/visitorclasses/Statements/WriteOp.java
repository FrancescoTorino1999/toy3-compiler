package compilatori.visitor.classes.visitorclasses.Statements;

import compilatori.visitor.classes.visitorclasses.Operators.StatOp;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;


import java.util.ArrayList;
import java.util.List;

public class WriteOp extends StatOp {
    private ArrayList<Visitable> exprOps;
    private Boolean hasNewLine;

    public WriteOp(ArrayList<Visitable> exprOps, Boolean hasNewLine) {
        this.exprOps = exprOps;
        this.hasNewLine = hasNewLine;
    }

    public WriteOp() {
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Visitable> getChildren() {
        List<Visitable> children = new ArrayList<>();

        if (exprOps != null && !exprOps.isEmpty()) {
            children.addAll(exprOps);
        }

        return children;
    }

    public ArrayList<Visitable> getExprOps() {
        return exprOps;
    }

    public void setExprOps(ArrayList<Visitable> exprOps) {
        this.exprOps = exprOps;
    }

    public Boolean getHasNewLine() {
        return hasNewLine;
    }

    public void setHasNewLine(Boolean hasNewLine) {
        this.hasNewLine = hasNewLine;
    }


}
