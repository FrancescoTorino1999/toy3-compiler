package compilatori.visitor.classes.visitorclasses.Expressions.Consts;

import compilatori.visitor.classes.visitorclasses.Expressions.TypeOrConstant;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

public class Const extends TypeOrConstant implements Visitable {
    private String lessema;

    public Const(String lessema) {
        this.lessema = lessema;
    }

    public Const() {
    }

    public String getLessema() {
        return lessema;
    }

    public void setLessema(String lessema) {
        this.lessema = lessema;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getContent() {
        return lessema;
    }
}