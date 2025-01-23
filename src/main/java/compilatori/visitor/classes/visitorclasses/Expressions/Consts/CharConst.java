package compilatori.visitor.classes.visitorclasses.Expressions.Consts;

import compilatori.visitor.classes.visitorclasses.baseclasses.TypeEnum;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

public class CharConst extends Const {
    private String lessema;
    private TypeEnum type;

    public CharConst(String lessema) {
        this.lessema = lessema;
        type = TypeEnum.CHAR;
    }

    public CharConst(String lessema, String lessema1) {
        super(lessema);
        this.lessema = lessema1;
        type = TypeEnum.CHAR;
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