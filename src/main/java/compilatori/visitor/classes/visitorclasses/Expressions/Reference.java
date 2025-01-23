package compilatori.visitor.classes.visitorclasses.Expressions;

import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;
import compilatori.visitor.classes.visitorclasses.baseclasses.Leaf;
import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

public class Reference extends Leaf implements Visitable {
    private Boolean isReference;

    public Reference(Boolean isReference) {
        this.isReference = isReference;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getContent() {
        return isReference ? "It is reference" : "It is not a reference";
    }

    public Boolean getReference() {
        return isReference;
    }

    public void setReference(Boolean reference) {
        isReference = reference;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "isReference=" + isReference +
                '}';
    }
}
