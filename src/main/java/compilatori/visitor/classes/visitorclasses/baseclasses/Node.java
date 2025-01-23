package compilatori.visitor.classes.visitorclasses.baseclasses;

import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

import java.util.Collections;
import java.util.List;

public class Node implements Visitable {


    public Node() {
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public List<Visitable> getChildren() {
        return Collections.emptyList();
    }

}
