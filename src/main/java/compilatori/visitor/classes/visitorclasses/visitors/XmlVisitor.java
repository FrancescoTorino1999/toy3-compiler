package compilatori.visitor.classes.visitorclasses.visitors;

import compilatori.visitor.classes.visitorclasses.baseclasses.Leaf;
import compilatori.visitor.classes.visitorclasses.baseclasses.Node;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;

import java.util.List;

public class XmlVisitor implements Visitor {

    private int indentLevel = 0;

    // Metodo per gestire la visita di un nodo generico
    @Override
    public void visit(Node node) {
        printIndent("<" + node.getClass().getSimpleName() + ">");

        // Visita ricorsiva dei figli
        List<Visitable> children = node.getChildren(); // `getChildren` ora restituisce `List<Visitable>`
        if (children != null && !children.isEmpty()) {
            indentLevel++; // Aumenta il livello di indentazione
            for (Visitable child : children) { // Itera sui figli generici
                if (child != null) {
                    child.accept(this); // Chiama il metodo accept del figlio
                }
            }
            indentLevel--; // Torna al livello precedente
        }

        printIndent("</" + node.getClass().getSimpleName() + ">");
    }

    // Metodo per gestire la visita di una foglia
    @Override
    public void visit(Leaf leaf) {
        // Stampa il contenuto della foglia
        printIndent("<" + leaf.getClass().getSimpleName() + ">" +
                leaf.getContent() +
                "</" + leaf.getClass().getSimpleName() + ">");
    }

    // Metodo di utilità per stampare l'indentazione
    private void printIndent(String text) {
        System.out.println("  ".repeat(indentLevel) + text);
    }
}