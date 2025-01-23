package compilatori.visitor.classes.visitorclasses.Operators;

import compilatori.visitor.classes.visitorclasses.baseclasses.TypeEnum;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.baseclasses.Node;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

public class ExprOp extends Node implements Visitable {
    private Mode mode;
    private TypeEnum type;
    public enum Mode {
        UNARY,
        BINARY,
        CONST,
        IOARGS,
        NONE;
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public ExprOp(Mode mode, TypeEnum tipo) {
        this.mode = mode;
        this.type = tipo;
    }

    public ExprOp() {
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setTyoe(TypeEnum tipo) {
        this.type = tipo;
    }
}
