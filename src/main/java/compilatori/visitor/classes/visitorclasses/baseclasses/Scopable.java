package compilatori.visitor.classes.visitorclasses.baseclasses;

import compilatori.visitor.classes.visitorclasses.Operators.BodyOp;

import java.util.Stack;

public interface Scopable {

    SymbolTable getSymbolTable();

    void setSymbolTable(SymbolTable symbolTable);
}
