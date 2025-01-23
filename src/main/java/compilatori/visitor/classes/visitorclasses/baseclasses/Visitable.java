package compilatori.visitor.classes.visitorclasses.baseclasses;

import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

public interface Visitable {
    public void accept(Visitor visitor);
}
