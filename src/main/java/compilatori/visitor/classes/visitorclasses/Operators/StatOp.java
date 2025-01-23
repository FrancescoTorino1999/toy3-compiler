package compilatori.visitor.classes.visitorclasses.Operators;

import compilatori.visitor.classes.visitorclasses.baseclasses.Node;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;


public class StatOp extends Node {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public StatOp() {
    }
}
