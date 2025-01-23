package compilatori.visitor.classes.visitorclasses.baseclasses;

import compilatori.visitor.classes.visitorclasses.visitors.Visitor;

import java.util.List;

public class Leaf implements Visitable {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public List<Visitable> getChildren() {
        return List.of();
    }

    public String getContent() {
        return "";
    }
}
