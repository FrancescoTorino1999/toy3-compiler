package compilatori.visitor.classes.visitorclasses.Expressions;

import compilatori.visitor.classes.visitorclasses.baseclasses.Leaf;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

public class TypeOrConstant extends Leaf {


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public TypeOrConstant() {
    }
}
