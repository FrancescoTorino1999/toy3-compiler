package compilatori.visitor.classes.visitorclasses.visitors;

import compilatori.visitor.classes.visitorclasses.baseclasses.Leaf;
import compilatori.visitor.classes.visitorclasses.baseclasses.Node;

public interface Visitor {
    void visit(Node node);
    void visit(Leaf node);
}
