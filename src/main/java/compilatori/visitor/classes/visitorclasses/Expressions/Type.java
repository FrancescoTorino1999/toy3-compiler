package compilatori.visitor.classes.visitorclasses.Expressions;

import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

public class Type extends TypeOrConstant {
    private String lessema;

    public Type(String lessema) {
        this.lessema = lessema;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getContent() {
        return lessema;
    }

    public String getLessema() {
        return lessema;
    }

    public void setLessema(String lessema) {
        this.lessema = lessema;
    }
}
