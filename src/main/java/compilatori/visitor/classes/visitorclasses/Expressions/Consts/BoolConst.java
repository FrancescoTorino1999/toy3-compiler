package compilatori.visitor.classes.visitorclasses.Expressions.Consts;

import compilatori.visitor.classes.visitorclasses.baseclasses.TypeEnum;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;


public class BoolConst extends Const {
    private String lessema;
    private TypeEnum type;


    public BoolConst(String lessema) {
        super();
        this.lessema = lessema;
        type = TypeEnum.BOOL;
    }

    public BoolConst(String lessema, String lessema1) {
        super(lessema);
        this.lessema = lessema1;
        type = TypeEnum.BOOL;
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

    @Override
    public String getContent() {
        return lessema;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }
}