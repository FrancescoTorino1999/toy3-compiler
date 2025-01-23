package compilatori.visitor.classes.visitorclasses.visitors;

import compilatori.visitor.classes.visitorclasses.Constants.Constants;
import compilatori.visitor.classes.visitorclasses.Expressions.Arith.*;
import compilatori.visitor.classes.visitorclasses.Expressions.Bool.AndOp;
import compilatori.visitor.classes.visitorclasses.Expressions.Bool.OrOp;
import compilatori.visitor.classes.visitorclasses.Expressions.FunCall;
import compilatori.visitor.classes.visitorclasses.Expressions.Identifier;
import compilatori.visitor.classes.visitorclasses.Expressions.OptType;
import compilatori.visitor.classes.visitorclasses.Expressions.Rels.*;
import compilatori.visitor.classes.visitorclasses.Operators.DefDeclOp;
import compilatori.visitor.classes.visitorclasses.Operators.ExprOp;
import compilatori.visitor.classes.visitorclasses.Operators.StatOp;
import compilatori.visitor.classes.visitorclasses.Statements.*;
import compilatori.visitor.classes.visitorclasses.baseclasses.*;
import compilatori.visitor.classes.visitorclasses.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TypeCheckingVisitor implements Visitor {

    public TypeCheckingVisitor() {

        this.isTypeCorrect = false;
        this.currSymbolTables = new Stack<>();
        this.currTable = new SymbolTable();
    }

    private boolean isTypeCorrect;
    private final Stack<SymbolTable> currSymbolTables;
    private SymbolTable currTable;

    private TypeEnum removeRefForEvaluating (TypeEnum typeEnum) {

        if(typeEnum == null)
            return null;

        if(typeEnum.equals(TypeEnum.REF_INT))
            return TypeEnum.INT;
        if(typeEnum.equals(TypeEnum.REF_DOUBLE))
            return TypeEnum.DOUBLE;
        if(typeEnum.equals(TypeEnum.REF_BOOL))
            return TypeEnum.BOOL;
        if(typeEnum.equals(TypeEnum.REF_CHAR))
            return TypeEnum.CHAR;
        if(typeEnum.equals(TypeEnum.REF_STRING))
            return TypeEnum.STRING;

        return typeEnum;

    }

    public TypeEnum evaluateOperationType(String op, TypeEnum op1, TypeEnum op2) throws BadOperation {

        op1 = removeRefForEvaluating(op1);
        op2 = removeRefForEvaluating(op2);

        try {
            if(op2 != null) {

                switch (op) {

                    case Constants.ADD_OP: {

                        if (op1.toString().equalsIgnoreCase(Constants.INT) && op2.toString().equalsIgnoreCase(Constants.INT))
                            return TypeEnum.INT;
                        else if (op1.toString().equalsIgnoreCase(Constants.INT) && op2.toString().equalsIgnoreCase(Constants.DOUBLE))
                            return TypeEnum.DOUBLE;
                        if (op1.toString().equalsIgnoreCase(Constants.DOUBLE) && op2.toString().equalsIgnoreCase(Constants.INT))
                            return TypeEnum.DOUBLE;
                        if (op1.toString().equalsIgnoreCase(Constants.DOUBLE) && op2.toString().equalsIgnoreCase(Constants.DOUBLE))
                            return TypeEnum.DOUBLE;
                        if(op1.toString().equalsIgnoreCase(Constants.STRING) && op2.toString().equalsIgnoreCase(Constants.STRING))
                            return TypeEnum.STRING;
                        if(op1.toString().equalsIgnoreCase(Constants.STRING) && op2.toString().equalsIgnoreCase(Constants.INT))
                            return TypeEnum.STRING;
                        if(op1.toString().equalsIgnoreCase(Constants.STRING) && op2.toString().equalsIgnoreCase(Constants.DOUBLE))
                            return TypeEnum.STRING;
                        if(op1.toString().equalsIgnoreCase(Constants.DOUBLE) && op2.toString().equalsIgnoreCase(Constants.STRING))
                            return TypeEnum.STRING;
                        if(op1.toString().equalsIgnoreCase(Constants.INT) && op2.toString().equalsIgnoreCase(Constants.STRING))
                            return TypeEnum.STRING;
                        else {
                            throw new BadOperation(op,op1.toString(),op2.toString());
                        }

                    }

                    case Constants.TIMES_OP, Constants.MINUS_OP: {
                        if (op1.toString().equalsIgnoreCase(Constants.INT) && op2.toString().equalsIgnoreCase(Constants.INT))
                            return TypeEnum.INT;
                        else if (op1.toString().equalsIgnoreCase(Constants.INT) && op2.toString().equalsIgnoreCase(Constants.DOUBLE))
                            return TypeEnum.DOUBLE;
                        if (op1.toString().equalsIgnoreCase(Constants.DOUBLE) && op2.toString().equalsIgnoreCase(Constants.INT))
                            return TypeEnum.DOUBLE;
                        if (op1.toString().equalsIgnoreCase(Constants.DOUBLE) && op2.toString().equalsIgnoreCase(Constants.DOUBLE))
                            return TypeEnum.DOUBLE;
                        else {
                            throw new BadOperation(op,op1.toString(),op2.toString());
                        }
                    }

                    case Constants.DIV_OP: {
                        if (op1.toString().equalsIgnoreCase(Constants.INT) && op2.toString().equalsIgnoreCase(Constants.INT))
                            return TypeEnum.DOUBLE;
                        else if (op1.toString().equalsIgnoreCase(Constants.INT) && op2.toString().equalsIgnoreCase(Constants.DOUBLE))
                            return TypeEnum.DOUBLE;
                        if (op1.toString().equalsIgnoreCase(Constants.DOUBLE) && op2.toString().equalsIgnoreCase(Constants.INT))
                            return TypeEnum.DOUBLE;
                        if (op1.toString().equalsIgnoreCase(Constants.DOUBLE) && op2.toString().equalsIgnoreCase(Constants.DOUBLE))
                            return TypeEnum.DOUBLE;
                        else {
                            throw new BadOperation(op,op1.toString(),op2.toString());
                        }
                    }

                    case Constants.AND_OP, Constants.OR_OP: {
                        if (op1.toString().equalsIgnoreCase(Constants.BOOL) && op2.toString().equalsIgnoreCase(Constants.BOOL))
                            return TypeEnum.BOOL;
                        else {
                            throw new BadOperation(op,op1.toString(),op2.toString());
                        }
                    }

                    case Constants.EQ_OP, Constants.GE_OP, Constants.GT_OP, Constants.LE_OP, Constants.LT_OP, Constants.NE_OP:{
                        if (op1.toString().equalsIgnoreCase(Constants.INT) && op2.toString().equalsIgnoreCase(Constants.INT))
                            return TypeEnum.BOOL;
                        else if (op1.toString().equalsIgnoreCase(Constants.INT) && op2.toString().equalsIgnoreCase(Constants.DOUBLE))
                            return TypeEnum.BOOL;
                        if (op1.toString().equalsIgnoreCase(Constants.DOUBLE) && op2.toString().equalsIgnoreCase(Constants.INT))
                            return TypeEnum.BOOL;
                        if (op1.toString().equalsIgnoreCase(Constants.DOUBLE) && op2.toString().equalsIgnoreCase(Constants.DOUBLE))
                            return TypeEnum.BOOL;
                        if (op1.toString().equalsIgnoreCase(Constants.STRING) && op2.toString().equalsIgnoreCase(Constants.STRING))
                            return TypeEnum.BOOL;
                        if (op1.toString().equalsIgnoreCase(Constants.CHAR) && op2.toString().equalsIgnoreCase(Constants.CHAR))
                            return TypeEnum.BOOL;
                        else {
                            throw new BadOperation(op,op1.toString(),op2.toString());
                        }
                    }

                }
            } else {

                switch (op) {

                    case Constants.UMINUS_OP: {
                        if (op1.toString().equalsIgnoreCase(Constants.INT))
                            return TypeEnum.INT;
                        else if (op1.toString().equalsIgnoreCase(Constants.DOUBLE))
                            return TypeEnum.DOUBLE;
                        else {
                            throw new BadOperation(op,op1.toString(), "");
                        }
                    }
                    case Constants.NOT_OP: {
                        if (op1.toString().equalsIgnoreCase(Constants.BOOL))
                            return TypeEnum.BOOL;
                        else {
                            throw new BadOperation(op,op1.toString(), "");
                        }
                    }
                }
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }




        return TypeEnum.VOID;
    }


    public Symbol lookUp(String value, String kind) throws VariableNotDeclaredException, FunctionNotDeclaredException {

        Stack<SymbolTable> clonedStack = new Stack<>();
        int x = 0;
        if(value.equals("sommac"))
            x = 0;
        for (SymbolTable currSymbolTable : currSymbolTables) {
            clonedStack.push(new SymbolTable(
                    currSymbolTable.getClassFathersName(),
                    new ArrayList<>(currSymbolTable.getSymbols())
            ));
        }

        while (!clonedStack.isEmpty()) {
            SymbolTable currSymbolTable = clonedStack.pop();
            for (Symbol symbol : currSymbolTable.getSymbols()) {
                if(kind.equals(Constants.FUNCTIONORPROCEDURE)) {
                    if (symbol.getSymbol().equals(value) && (symbol.getKind().equals(Kind.FUNCTION) || symbol.getKind().equals(Kind.PROCEDURE))) {
                        return symbol;
                    }
                } else {
                    if (symbol.getSymbol().equals(value) && !(symbol.getKind().equals(Kind.FUNCTION) || symbol.getKind().equals(Kind.PROCEDURE))) {
                        return symbol;
                    }
                }

            }
        }

        if(kind.equals(Constants.FUNCTIONORPROCEDURE)) {
            throw new FunctionNotDeclaredException(value);
        } else {
            throw new VariableNotDeclaredException(value);
        }
    }


    public boolean isTypeCorrect() {
        return isTypeCorrect;
    }

    public void setTypeCorrect(boolean typeCorrect) {
        isTypeCorrect = typeCorrect;
    }


    @Override
    public void visit(Node node) {

        try {

            switch (node.getClass().getSimpleName()) {

                case Constants.PROGRAM_OP, Constants.BEGIN_END_OP: {

                    currSymbolTables.add(((Scopable) node).getSymbolTable());

                    currTable = currSymbolTables.peek();

                    List<Visitable> childrenFirst = node.getChildren();
                    if (childrenFirst != null && !childrenFirst.isEmpty()) {
                        for (Visitable child : childrenFirst) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }
                    }

                    currSymbolTables.pop();

                    break;
                }

                case Constants.LET_OP: {

                    currSymbolTables.add(((LetOp)node).getSymbolTable());

                    currTable = currSymbolTables.peek();

                    List<Visitable> childrenFirst = node.getChildren();
                    if (childrenFirst != null && !childrenFirst.isEmpty()) {
                        for (Visitable child : childrenFirst) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }
                    }

                    currSymbolTables.pop();

                    break;
                }

                case Constants.WHILE_OP: {

                    WhileOp whileOp = (WhileOp) node;

                    try {


                        whileOp.getExprOp().accept(this);

                        String type = resolveType(whileOp.getExprOp()).toString();

                        if (!type.equals(TypeEnum.BOOL.toString()))
                            throw new BadWhileCondition();
                    } catch (Exception e) {
                        throw new BadWhileCondition();
                    }

                    currTable = whileOp.getBodyOp().getSymbolTable();

                    currSymbolTables.add(currTable);


                    List<Visitable> childrenFirst = whileOp.getBodyOp().getChildren();
                    if (childrenFirst != null && !childrenFirst.isEmpty()) {
                        for (Visitable child : childrenFirst) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }
                    }

                    currSymbolTables.pop();

                    break;
                }

                case Constants.IF_THEN_ELSE_OP: {

                    IfThenElseOp ifThenElseOp = (IfThenElseOp) node;

                    try {

                        ifThenElseOp.getExprOp().accept(this);

                        String type = resolveType(ifThenElseOp.getExprOp()).toString();

                        if (!(type.equals(TypeEnum.BOOL.toString()) || type.equals(TypeEnum.REF_BOOL.toString())))
                            throw new BadIfCondition();
                    } catch (Exception e) {
                        throw new BadIfCondition();
                    }

                    currTable = ifThenElseOp.getThenBody().getSymbolTable();

                    currSymbolTables.add(currTable);

                    List<Visitable> childrenFirst = ifThenElseOp.getThenBody().getChildren();
                    if (childrenFirst != null && !childrenFirst.isEmpty()) {
                        for (Visitable child : childrenFirst) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }
                    }

                    currSymbolTables.pop();

                    currTable = ifThenElseOp.getElseBody().getSymbolTable();

                    currSymbolTables.add(currTable);

                    childrenFirst = ifThenElseOp.getElseBody().getChildren();
                    if (childrenFirst != null && !childrenFirst.isEmpty()) {
                        for (Visitable child : childrenFirst) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }
                    }

                    currSymbolTables.pop();

                    break;
                }

                case Constants.IF_THEN_OP: {

                    IfThenOp ifThenOp = (IfThenOp) node;

                    try {

                        ifThenOp.getExprOp().accept(this);

                        String type = resolveType(ifThenOp.getExprOp()).toString();

                        if (!type.equals(TypeEnum.BOOL.toString()))
                            throw new BadIfCondition();
                    } catch (Exception e) {
                        throw new BadIfCondition();
                    }

                    currTable = ifThenOp.getThenBody().getSymbolTable();

                    currSymbolTables.add(currTable);

                    List<Visitable> childrenFirst = ifThenOp.getThenBody().getChildren();
                    if (childrenFirst != null && !childrenFirst.isEmpty()) {
                        for (Visitable child : childrenFirst) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }
                    }

                    currSymbolTables.pop();


                    break;

                }

                case Constants.ASSIGN_OP: {

                    AssignOp assignOp = (AssignOp) node;

                    List<Visitable> childrenFirst = assignOp.getChildren();
                    if (childrenFirst != null && !childrenFirst.isEmpty()) {
                        for (Visitable child : childrenFirst) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }
                    }

                    ArrayList<Identifier> identifiers = assignOp.getIdentifiers();
                    ArrayList<Visitable> exprOps = assignOp.getExprOps();

                    if(identifiers.size() != exprOps.size()) {

                        if(exprOps.size() != 1) {

                            throw new BadAssignment(concatIdNames(identifiers));

                        } else {

                            String typeExpr = resolveType(exprOps.get(0)).toString();

                            for(Identifier id : identifiers) {

                                if(!resolveType(id).toString().equals(typeExpr))
                                    throw new BadAssignment(id.getLessema());

                            }
                        }

                    } else {

                        for (int i = 0; i < exprOps.size(); i++) {

                            String typeExpr = resolveType(exprOps.get(i)).toString();
                            String typeId = resolveType((Identifier) identifiers.get(i)).toString();

                            if(!((typeExpr.equals(TypeEnum.INT.toString()) || typeExpr.equals(TypeEnum.REF_INT.toString())) && (typeId.equals(TypeEnum.DOUBLE.toString())) || (typeId.equals(TypeEnum.REF_DOUBLE.toString())))) {
                                if (!(typeExpr.equals(typeId) || ("REF_" + typeExpr).equals(typeId)))
                                    throw new BadAssignment(identifiers.get(i).getLessema());
                            }

                        }

                    }

                    break;
                }

                case Constants.FUN_CALL_OP: {

                    FunCall funCall = ((FunCallOp) node).getFuncall();

                    Identifier identifier = funCall.getIdentifier();

                    Symbol funcSym = lookUp(identifier.getLessema(), Constants.FUNCTIONORPROCEDURE);

                    funCall.setFunction(funcSym.getKind().equals(Kind.FUNCTION));
                    funCall.setProcedure(!funcSym.getKind().equals(Kind.FUNCTION));
                    funCall.setInputParameters(funcSym.getTypeSym().getInputParameters());
                    funCall.setType(funcSym.typeSym.getTypeEnum());
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }


                    identifier = funCall.getIdentifier();

                    if(funCall.getInputParameters().size() != funCall.getExprOps().size())
                        throw  new BadCallFunction(identifier.getLessema());

                    for(int i = 0; i < funCall.getInputParameters().size(); i++) {

                        List<String> keys = new ArrayList<>(funCall.getInputParameters().keySet());
                        String typeParam = funCall.getInputParameters().get(keys.get(i)).toString();
                        String typeVariable = resolveType(funCall.getExprOps().get(i)).toString();

                        if(!(typeParam.equals(typeVariable) || (typeParam).equals("REF_"+typeVariable)))
                            throw  new BadCallFunction(identifier.getLessema());

                    }


                    List<Visitable> childrenFirst = node.getChildren();
                    if (childrenFirst != null && !childrenFirst.isEmpty()) {
                        for (Visitable child : childrenFirst) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }
                    }

                    break;
                }

                case Constants.FUN_CALL: {

                    FunCall funCall = ((FunCall) node);

                    Identifier identifier = funCall.getIdentifier();

                    Symbol funcSym = lookUp(identifier.getLessema(), Constants.FUNCTIONORPROCEDURE);

                    funCall.setFunction(funcSym.getKind().equals(Kind.FUNCTION));
                    funCall.setProcedure(!funcSym.getKind().equals(Kind.FUNCTION));
                    funCall.setInputParameters(funcSym.getTypeSym().getInputParameters());
                    funCall.setType(funcSym.typeSym.getTypeEnum());
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                if(child.getClass().getSimpleName().equals(Constants.IDENTIFIER) && ((Identifier)child).getLessema().equals(funcSym.getSymbol()))
                                    visitFunctionId(child);
                                else
                                    child.accept(this);
                            }
                        }

                    }


                    identifier = funCall.getIdentifier();

                    if(funCall.getInputParameters().size() != funCall.getExprOps().size())
                        throw  new BadCallFunction(identifier.getLessema());

                    for(int i = 0; i < funCall.getInputParameters().size(); i++) {

                        List<String> keys = new ArrayList<>(funCall.getInputParameters().keySet());
                        String typeParam = funCall.getInputParameters().get(keys.get(i)).toString();
                        String typeVariable = resolveType(funCall.getExprOps().get(i)).toString();

                        if(!(typeParam.equals(typeVariable) || (typeParam).equals("REF_"+typeVariable)))
                            throw  new BadCallFunction(identifier.getLessema());

                    }


                    List<Visitable> childrenFirst = node.getChildren();
                    if (childrenFirst != null && !childrenFirst.isEmpty()) {
                        for (Visitable child : childrenFirst) {
                            if (child != null) {
                                if(child.getClass().getSimpleName().equals(Constants.IDENTIFIER) && ((Identifier)child).getLessema().equals(funcSym.getSymbol()))
                                    visitFunctionId(child);
                                else
                                    child.accept(this);
                            }
                        }
                    }

                    break;
                }


                case Constants.DEF_DECL_OP: {

                    DefDeclOp defDeclOp = (DefDeclOp) node;

                    TypeEnum typeEnum = resolveOptType(defDeclOp.getOptType());

                    currTable = defDeclOp.getBodyOp().getSymbolTable();

                    currSymbolTables.add(currTable);

                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                if(child.getClass().getSimpleName().equals(Constants.IDENTIFIER))
                                    visitFunctionId(child);
                                else
                                    child.accept(this);
                            }
                        }

                    }

                    if (typeEnum.toString().equals(TypeEnum.VOID.toString())) { //caso procedure

                        for (StatOp statOp : defDeclOp.getBodyOp().getStatOps()) {

                            if(statOp.getClass().getSimpleName().equals(Constants.RETURN_OP))
                                throw new BadReturnProcedureStatement();

                        }


                    } else { //caso funzione

                        boolean returnFound = false;

                        for (StatOp statOp : defDeclOp.getBodyOp().getStatOps()) {

                            if(statOp.getClass().getSimpleName().equals(Constants.RETURN_OP)) {
                                returnFound = true;

                                ReturnOp returnOp = (ReturnOp) statOp;
                                TypeEnum returnType = resolveType(returnOp.getExprOp());
                                if(!returnType.toString().equals(typeEnum.toString()))
                                    throw new BadReturnFunzioneStatement();
                            }

                        }

                        if(!returnFound)
                            throw new BadReturnFunzioneStatement();



                    }

                    currSymbolTables.pop();

                    break;
                }

                case Constants.ADD_OP: {
                    AddOp addOp = (AddOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum leftType = resolveType(addOp.getExpr1());
                    TypeEnum rightType = resolveType(addOp.getExpr2());
                    addOp.setType(evaluateOperationType(Constants.ADD_OP, leftType, rightType));
                    break;
                }

                case Constants.DIV_OP: {
                    DivOp divOp = (DivOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum leftType = resolveType(divOp.getExpr1());
                    TypeEnum rightType = resolveType(divOp.getExpr2());
                    divOp.setType(evaluateOperationType(Constants.DIV_OP, leftType, rightType));
                    break;
                }

                case Constants.TIMES_OP: {
                    TimesOp timesOp = (TimesOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum leftType = resolveType(timesOp.getExpr1());
                    TypeEnum rightType = resolveType(timesOp.getExpr2());
                    timesOp.setType(evaluateOperationType(Constants.TIMES_OP, leftType, rightType));
                    break;
                }

                case Constants.MINUS_OP: {
                    MinusOp minusOp = (MinusOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum leftType = resolveType(minusOp.getExpr1());
                    TypeEnum rightType = resolveType(minusOp.getExpr2());
                    minusOp.setType(evaluateOperationType(Constants.MINUS_OP, leftType, rightType));
                    break;
                }

                case Constants.NOT_OP: {
                    NotOp notOp = (NotOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum exprType = resolveType(notOp.getExpr1());
                    notOp.setType(evaluateOperationType(Constants.NOT_OP, exprType, null));
                    break;
                }

                case Constants.UMINUS_OP: {
                    UminusOp uminusOp = (UminusOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum exprType = resolveType(uminusOp.getExpr1());
                    uminusOp.setType(evaluateOperationType(Constants.UMINUS_OP, exprType, null));
                    break;
                }

                case Constants.AND_OP: {
                    AndOp andOp = (AndOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum leftType = resolveType(andOp.getExpr1());
                    TypeEnum rightType = resolveType(andOp.getExpr2());
                    andOp.setType(evaluateOperationType(Constants.AND_OP, leftType, rightType));
                    break;
                }

                case Constants.OR_OP: {
                    OrOp orOp = (OrOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum leftType = resolveType(orOp.getExpr1());
                    TypeEnum rightType = resolveType(orOp.getExpr2());
                    orOp.setType(evaluateOperationType(Constants.OR_OP, leftType, rightType));
                    break;
                }

                case Constants.EQ_OP: {
                    EqOp eqOp = (EqOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum leftType = resolveType(eqOp.getExpr1());
                    TypeEnum rightType = resolveType(eqOp.getExpr2());
                    eqOp.setType(evaluateOperationType(Constants.EQ_OP, leftType, rightType));
                    break;
                }

                case Constants.GE_OP: {
                    GeOp geOp = (GeOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum leftType = resolveType(geOp.getExpr1());
                    TypeEnum rightType = resolveType(geOp.getExpr2());
                    geOp.setType(evaluateOperationType(Constants.GE_OP, leftType, rightType));
                    break;
                }

                case Constants.GT_OP: {
                    GtOp gtOp = (GtOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum leftType = resolveType(gtOp.getExpr1());
                    TypeEnum rightType = resolveType(gtOp.getExpr2());
                    gtOp.setType(evaluateOperationType(Constants.GT_OP, leftType, rightType));
                    break;
                }

                case Constants.LE_OP: {
                    LeOp leOp = (LeOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum leftType = resolveType(leOp.getExpr1());
                    TypeEnum rightType = resolveType(leOp.getExpr2());
                    leOp.setType(evaluateOperationType(Constants.LE_OP, leftType, rightType));
                    break;
                }

                case Constants.LT_OP: {
                    LtOp ltOp = (LtOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum leftType = resolveType(ltOp.getExpr1());
                    TypeEnum rightType = resolveType(ltOp.getExpr2());
                    ltOp.setType(evaluateOperationType(Constants.LT_OP, leftType, rightType));
                    break;
                }

                case Constants.NE_OP: {
                    NeOp neOp = (NeOp) node;
                    List<Visitable> children = node.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (Visitable child : children) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }

                    }
                    TypeEnum leftType = resolveType(neOp.getExpr1());
                    TypeEnum rightType = resolveType(neOp.getExpr2());
                    neOp.setType(evaluateOperationType(Constants.NE_OP, leftType, rightType));
                    break;
                }

                default: {

                    List<Visitable> childrenFirst = node.getChildren();
                    if (childrenFirst != null && !childrenFirst.isEmpty()) {
                        for (Visitable child : childrenFirst) {
                            if (child != null) {
                                child.accept(this);
                            }
                        }
                    }

                    break;
                }


            }
        } catch (Exception e) {
            isTypeCorrect = false;
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void visit(Leaf node) {
        switch (node.getClass().getSimpleName()) {
            case Constants.IDENTIFIER: {
                Identifier identifier = (Identifier) node;
                String value = ((Identifier) node).getLessema();
                try {
                    ((Identifier) node).setType(lookUp(((Identifier) node).getLessema(), Constants.VARIABLEORCONSTANT).getTypeSym().getTypeEnum());
                } catch (VariableNotDeclaredException e) {
                    throw new RuntimeException(e);
                } catch (FunctionNotDeclaredException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public void visitFunctionId (Visitable node) {
        Identifier identifier = (Identifier) node;
        String value = ((Identifier) node).getLessema();
        try {
            ((Identifier) node).setType(lookUp(((Identifier) node).getLessema(), Constants.FUNCTIONORPROCEDURE).getTypeSym().getTypeEnum());
        } catch (VariableNotDeclaredException e) {
            throw new RuntimeException(e);
        } catch (FunctionNotDeclaredException e) {
            throw new RuntimeException(e);
        }
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

    private TypeEnum resolveTypeForFunctions (Visitable expr) {

        TypeEnum typeEnumToRet = null;

        switch (expr.getClass().getSimpleName()) {
            case Constants.IDENTIFIER:
                try {
                    typeEnumToRet = (lookUp(((Identifier) expr).getLessema(), Constants.FUNCTIONORPROCEDURE).getTypeSym().getTypeEnum());
                } catch (VariableNotDeclaredException e) {
                    throw new RuntimeException(e);
                } catch (FunctionNotDeclaredException e) {
                    throw new RuntimeException(e);
                }
                break;
            case Constants.INT_CONST:
                typeEnumToRet =  TypeEnum.INT;
                break;
            case Constants.DOUBLE_CONST:
                typeEnumToRet =  TypeEnum.DOUBLE;
                break;
            case Constants.BOOL_CONST:
                typeEnumToRet =  TypeEnum.BOOL;
                break;
            case Constants.CHAR_CONST:
                typeEnumToRet =  TypeEnum.CHAR;
                break;
            case Constants.STRING_CONST:
                typeEnumToRet =  TypeEnum.STRING;
                break;
            case Constants.FUN_CALL:
                typeEnumToRet =  ((FunCall) expr).getIdentifier().getType();
                break;
            default:
                typeEnumToRet =  ((ExprOp) expr).getType();
                break;
        }

        return typeEnumToRet;
    }

    private TypeEnum resolveType(Visitable expr) {

        TypeEnum typeEnumToRet = null;

        switch (expr.getClass().getSimpleName()) {
            case Constants.IDENTIFIER:
                try {
                    typeEnumToRet = (lookUp(((Identifier) expr).getLessema(), Constants.VARIABLEORCONSTANT).getTypeSym().getTypeEnum());
                } catch (VariableNotDeclaredException e) {
                    throw new RuntimeException(e);
                } catch (FunctionNotDeclaredException e) {
                    throw new RuntimeException(e);
                }
                break;
            case Constants.INT_CONST:
                typeEnumToRet =  TypeEnum.INT;
                break;
            case Constants.DOUBLE_CONST:
                typeEnumToRet =  TypeEnum.DOUBLE;
                break;
            case Constants.BOOL_CONST:
                typeEnumToRet =  TypeEnum.BOOL;
                break;
            case Constants.CHAR_CONST:
                typeEnumToRet =  TypeEnum.CHAR;
                break;
            case Constants.STRING_CONST:
                typeEnumToRet =  TypeEnum.STRING;
                break;
            case Constants.FUN_CALL:
                typeEnumToRet =  ((FunCall) expr).getIdentifier().getType();
                break;
            default:
                typeEnumToRet =  ((ExprOp) expr).getType();
                break;
        }

        return typeEnumToRet;
    }

    private TypeEnum resolveOptType(OptType optType) {
        if(optType == null)
            return TypeEnum.VOID;
        switch (optType.getType().getLessema()) {
            case Constants.INT -> {
                return TypeEnum.INT;
            }
            case Constants.CHAR -> {
                return TypeEnum.CHAR;
            }
            case Constants.STRING -> {
                return TypeEnum.STRING;
            }
            case Constants.DOUBLE -> {
                return TypeEnum.DOUBLE;
            }
            case Constants.BOOL -> {
                return TypeEnum.BOOL;
            }
        }
        return TypeEnum.VOID;
    }

    protected String concatIdNames (ArrayList<Identifier> identifiers) {

        StringBuilder strToRet = new StringBuilder();
        for (Identifier id : identifiers) {

            strToRet.append(id.getLessema()).append(" ,");

        }

        return strToRet.substring(0, strToRet.length() -2) + " ";

    }
}

