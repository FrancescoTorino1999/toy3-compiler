package compilatori.visitor.classes.visitorclasses.visitors;

import compilatori.visitor.classes.visitorclasses.Constants.Constants;
import compilatori.visitor.classes.visitorclasses.Operators.*;
import compilatori.visitor.classes.visitorclasses.Statements.IfThenElseOp;
import compilatori.visitor.classes.visitorclasses.Statements.IfThenOp;
import compilatori.visitor.classes.visitorclasses.Statements.LetOp;
import compilatori.visitor.classes.visitorclasses.Statements.WhileOp;
import compilatori.visitor.classes.visitorclasses.baseclasses.*;
import compilatori.visitor.classes.visitorclasses.exceptions.ScopingException;

import java.util.*;

public class SymbolTableVisitor implements Visitor {

    private Stack<SymbolTable> typeEnvironment = new Stack<>();

    @Override
    public void visit(Node node) {

        switch (node.getClass().getSimpleName()) {

            case Constants.PROGRAM_OP: {
                SymbolTable programSymbolTable = new SymbolTable(Constants.PROGRAM_OP, new ArrayList<>());

                typeEnvironment.add(programSymbolTable);
                List<Visitable> children = node.getChildren();
                if (children != null && !children.isEmpty()) {
                    for (Visitable child : children) {
                        if (child != null) {
                            child.accept(this);
                        }
                    }
                }

                ((ProgramOp) node).setSymbolTable(programSymbolTable);
                break;
            }

            case Constants.LET_OP: {
                SymbolTable letOpSymbolTable = new SymbolTable(Constants.LET_OP, new ArrayList<>());

                typeEnvironment.add(letOpSymbolTable);
                List<Visitable> children = node.getChildren();
                if (children != null && !children.isEmpty()) {
                    for (Visitable child : children) {
                        if (child != null) {
                            child.accept(this);
                        }
                    }
                }

                ((LetOp) node).setSymbolTable(letOpSymbolTable);
                break;
            }

            case Constants.DEF_DECL_OP: {
                DefDeclOp defDeclOp = (DefDeclOp) node;
                SymbolTable symbolTable = typeEnvironment.peek();
                Kind kind;
                String type = Constants.VOID;

                if (defDeclOp.getOptType() != null) {
                    type = defDeclOp.getOptType().getType().getContent();
                    kind = Kind.FUNCTION;
                } else {
                    kind = Kind.PROCEDURE;
                }

                LinkedHashMap<String, TypeEnum> inputParameters = new LinkedHashMap<>();
                String functionName = defDeclOp.getIdentifier().getContent();
                SymbolTable localSymbolTable = new SymbolTable(functionName, new ArrayList<>());

                for (ParDeclOp par : defDeclOp.getParDeclOps()) {
                    TypeEnum typeEnum = fromLexToEnum(par.getType().getContent(), par.getpVarOp().get(0).getIsRef().getReference());
                    inputParameters.put(par.getpVarOp().get(0).getIdentifier().getContent(), typeEnum);
                    String value = par.getpVarOp().get(0).getIdentifier().getContent();
                    TypeSym typeSym = new TypeSym(false, true, typeEnum, new LinkedHashMap<>());
                    try {
                        localSymbolTable.addVariable(new Symbol(value, Kind.PARAMETER, typeSym));
                    } catch (ScopingException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                }

                TypeSym typeSym = new TypeSym(true, false, fromLexToEnum(type, false), inputParameters);
                Symbol symbol = new Symbol(functionName, kind, typeSym);
                try {
                    symbolTable.addVariable(symbol);
                } catch (ScopingException e) {
                    System.out.println(e.getMessage());
                    return;
                }

                typeEnvironment.add(localSymbolTable);

                defDeclOp.getBodyOp().accept(this);

                break;
            }

            case Constants.BODY_OP: {

                SymbolTable bodyOpSymbolTable = typeEnvironment.peek();

                List<Visitable> children = node.getChildren();
                if (children != null && !children.isEmpty()) {
                    for (Visitable child : children) {
                        if (child != null) {
                            child.accept(this);
                        }
                    }
                }


                ((BodyOp) node).setSymbolTable(bodyOpSymbolTable);

                typeEnvironment.pop();

                break;
            }

            case Constants.BEGIN_END_OP: {
                SymbolTable beginEndOpSymbolTable = new SymbolTable(Constants.BEGIN_END_OP, new ArrayList<>());
                typeEnvironment.add(beginEndOpSymbolTable);

                List<Visitable> children = node.getChildren();
                if (children != null && !children.isEmpty()) {
                    for (Visitable child : children) {
                        if (child != null) {
                            child.accept(this);
                        }
                    }
                }


                ((BeginEndOp) node).setSymbolTable(beginEndOpSymbolTable);

                typeEnvironment.pop();

                break;
            }

            case Constants.WHILE_OP: {
                WhileOp whileOp = (WhileOp) node;
                SymbolTable localSymbolTable = new SymbolTable(Constants.WHILE_OP, new ArrayList<>());
                typeEnvironment.add(localSymbolTable);

                whileOp.getBodyOp().accept(this);

                break;
            }

            case Constants.IF_THEN_OP: {
                IfThenOp ifThenOp = (IfThenOp) node;
                SymbolTable localSymbolTable = new SymbolTable(Constants.IF_THEN_OP, new ArrayList<>());
                typeEnvironment.add(localSymbolTable);

                ifThenOp.getThenBody().accept(this);

                break;
            }

            case Constants.IF_THEN_ELSE_OP: {
                IfThenElseOp ifThenElseOp = (IfThenElseOp) node;

                // Gestione del blocco THEN
                SymbolTable localSymbolTable = new SymbolTable(Constants.IF_THEN_ELSE_OP + " - Then", new ArrayList<>());
                typeEnvironment.add(localSymbolTable);
                ifThenElseOp.getThenBody().accept(this);

                // Gestione del blocco ELSE
                localSymbolTable = new SymbolTable(Constants.IF_THEN_ELSE_OP + " - Else", new ArrayList<>());
                typeEnvironment.add(localSymbolTable);
                ifThenElseOp.getElseBody().accept(this);

                break;
            }

            case Constants.VAR_DECL_OP: {
                VarDeclOp declOp = (VarDeclOp) node;
                SymbolTable actualSymbolTable = typeEnvironment.peek(); // Usa peek per non rimuovere la tabella
                ArrayList<VarOpInitOp> varsOptInitOps = declOp.getVarsOptInitOp().getVarsOptInitOp();
                for (VarOpInitOp varOpInitOp : varsOptInitOps) {
                    TypeEnum t;
                    Kind k;

                    if (declOp.getVarsOptInitOp().getConstant().getClass().getSimpleName().equals(Constants.TYPE)) {
                        t = fromLexToEnum(declOp.getVarsOptInitOp().getConstant().getContent(), false);
                        k = Kind.VARIABLE;
                    } else {
                        t = fromLexToEnum(declOp.getVarsOptInitOp().getConstant().getClass().getSimpleName(), false);
                        k = Kind.CONSTANT;
                    }

                    String value = varOpInitOp.getIdentifier().getContent();
                    TypeSym typeSym = new TypeSym(false, true, t, new LinkedHashMap<>());
                    Symbol symbol = new Symbol(value, k, typeSym);
                    try {
                        actualSymbolTable.addVariable(symbol);
                    } catch (ScopingException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                }

                break;
            }

            default: {
                List<Visitable> children = node.getChildren();
                if (children != null && !children.isEmpty()) {
                    for (Visitable child : children) {
                        if (child != null) {
                            child.accept(this);
                        }
                    }
                }
                break;
            }
        }
    }

    @Override
    public void visit(Leaf node) {

    }

    public void setTypeEnvironment(Stack<SymbolTable> typeEnvironment) {
        this.typeEnvironment = typeEnvironment;
    }

    public TypeEnum fromLexToEnum(String lessema, Boolean ref) {

        if (ref) {
            switch (lessema) {
                case Constants.STRING:
                    return TypeEnum.REF_STRING;
                case Constants.CHAR:
                    return TypeEnum.REF_CHAR;
                case Constants.DOUBLE:
                    return TypeEnum.REF_DOUBLE;
                case Constants.INT:
                    return TypeEnum.REF_INT;
                case Constants.BOOL:
                    return TypeEnum.REF_BOOL;
            }
        } else {
            switch (lessema) {
                case Constants.STRING, Constants.STRING_CONST:
                    return TypeEnum.STRING;
                case Constants.CHAR, Constants.CHAR_CONST:
                    return TypeEnum.CHAR;
                case Constants.DOUBLE, Constants.DOUBLE_CONST:
                    return TypeEnum.DOUBLE;
                case Constants.INT, Constants.INT_CONST:
                    return TypeEnum.INT;
                case Constants.BOOL, Constants.BOOL_CONST:
                    return TypeEnum.BOOL;
            }
        }

        return TypeEnum.VOID;
    }
}