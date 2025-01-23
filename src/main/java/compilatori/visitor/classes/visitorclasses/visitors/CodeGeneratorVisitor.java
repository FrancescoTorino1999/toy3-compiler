package compilatori.visitor.classes.visitorclasses.visitors;

import compilatori.visitor.classes.visitorclasses.Constants.Constants;
import compilatori.visitor.classes.visitorclasses.Expressions.Arith.*;
import compilatori.visitor.classes.visitorclasses.Expressions.Bool.AndOp;
import compilatori.visitor.classes.visitorclasses.Expressions.Bool.OrOp;
import compilatori.visitor.classes.visitorclasses.Expressions.Consts.*;
import compilatori.visitor.classes.visitorclasses.Expressions.FunCall;
import compilatori.visitor.classes.visitorclasses.Expressions.Identifier;
import compilatori.visitor.classes.visitorclasses.Expressions.Rels.*;
import compilatori.visitor.classes.visitorclasses.Expressions.Type;
import compilatori.visitor.classes.visitorclasses.Operators.*;
import compilatori.visitor.classes.visitorclasses.Statements.*;
import compilatori.visitor.classes.visitorclasses.baseclasses.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CodeGeneratorVisitor implements Visitor {

    private StringBuilder generatedCode;

    public CodeGeneratorVisitor() {
        this.generatedCode = new StringBuilder();
    }

    @Override
    public void visit(Node node) {

        switch (node.getClass().getSimpleName()) {

            case Constants.PROGRAM_OP: {
                generatedCode.append("#include <stdio.h>\n");
                generatedCode.append("#include <stdbool.h>\n");
                generatedCode.append("#include <string.h>\n");
                generatedCode.append("#include <stdlib.h>\n");
                generatedCode.append("#define MAXSIZE 100\n");
                CodeGeneratorUtils.addHelperFunctions(generatedCode);

                ProgramOp programOp = (ProgramOp) node;

                for(Symbol symbol : programOp.getSymbolTable().getSymbols()) {

                    if(symbol.getKind().equals(Kind.PROCEDURE) || symbol.getKind().equals(Kind.FUNCTION)) {

                        generatedCode.append(fromEnumToType(symbol.getTypeSym().getTypeEnum())).append(" ");
                        generatedCode.append(Constants.FUNCTION_PREFIX).append(symbol.getSymbol()).append("(");
                        Set<String> keys = symbol.getTypeSym().getInputParameters().keySet();
                        for(String key : keys) {

                            generatedCode.append(fromEnumToType(symbol.getTypeSym().getInputParameters().get(key)));
                            generatedCode.append(key).append(", ");

                        }

                        if(!keys.isEmpty())
                            generatedCode.setLength(generatedCode.length() - 2);
                        generatedCode.append(");\n");

                        generatedCode.append("//placeholderForLetOpFunctions\n");


                    }

                }

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

            case Constants.BEGIN_END_OP: {

                visitBeginEndOp(node, true);


                break;
            }

            case Constants.LET_OP: {

                generatedCode.append("{\n");

                List<Visitable> children = node.getChildren();
                if (children != null && !children.isEmpty()) {
                    for (Visitable child : children) {
                        if (child != null) {
                            if(child.getClass().getSimpleName().equals(Constants.BEGIN_END_OP)) {
                                visitBeginEndOp((BeginEndOp)child, false);
                            } else {
                                child.accept(this);
                            }
                        }
                    }

                }

                generatedCode.append("}\n");


                break;
            }

            case Constants.VARS_INIT_OP: {

                VarsOptInitOp varsOptInitOp = (VarsOptInitOp) node;

                ArrayList<VarOpInitOp> varOpInitOps = varsOptInitOp.getVarsOptInitOp();

                Visitable constant = varsOptInitOp.getConstant();

                for(VarOpInitOp varOpInitOp : varOpInitOps) {

                    String type = resolveType(varOpInitOp.getIdentifier().getType());

                    String name = varOpInitOp.getIdentifier().getLessema();

                    if(constant.getClass().getSimpleName().equals(Constants.TYPE)) {

                        Type t = (Type) constant;

                        if(type.equals(Constants.STRINGC)) {
                            generatedCode.append(type, 0, type.length() -1)
                                    .append(" ")
                                    .append(name)
                                    .append("[MAXSIZE];\n");
                        } else {
                            generatedCode.append(type).append(" ").append(name).append(" = ");


                            switch (t.getLessema()) {

                                case Constants.INT:
                                    generatedCode.append("0");
                                    generatedCode.append(";\n");
                                    break;

                                case Constants.DOUBLE:
                                    generatedCode.append("0.0");
                                    generatedCode.append(";\n");
                                    break;

                                case Constants.CHAR:
                                    generatedCode.append("'0'");
                                    generatedCode.append(";\n");
                                    break;

                                case Constants.BOOL:
                                    generatedCode.append("false");
                                    generatedCode.append(";\n");
                                    break;

                                case Constants.STRING:
                                    generatedCode.append("\"\"");
                                    break;
                            }

                        }

                    } else {

                        if(type.equals(Constants.STRINGC) || type.equals(Constants.STRINGV)) {

                            generatedCode.append(type, 0, type.length() -1)
                                    .append(" ")
                                    .append(name)
                                    .append("[MAXSIZE]");

                            generatedCode.append(" = ");

                            constant.accept(this);

                            generatedCode.append(";\n");

                        } else {

                            generatedCode.append(type).append(" ").append(name).append(" = ");

                            constant.accept(this);

                            generatedCode.append(";\n");

                        }



                    }
                }

                break;
            }

            case Constants.DEF_DECL_OP: {

                DefDeclOp defDeclOp = (DefDeclOp) node;

                String name = defDeclOp.getIdentifier().getLessema();

                String type;

                if(defDeclOp.getOptType() != null) {
                    type = defDeclOp.getOptType().getType().getLessema();
                    if(type.equals(Constants.STRING))
                        type = Constants.STRINGC;
                }
                else
                    type = Constants.VOID;

                ArrayList<ParDeclOp> parDeclOps = defDeclOp.getParDeclOps();

                generatedCode.append(type).append(" ").append(Constants.FUNCTION_PREFIX).append(name).append(" (");

                for(ParDeclOp parDeclOp : parDeclOps) {

                    for(PVarOp pVarOp : parDeclOp.getpVarOp()) {

                        String actualType = parDeclOp.getType().getLessema();

                        if(actualType.equals(Constants.STRING)) //caso ** dell'input delle funzioni
                            generatedCode.append("char* ").append(pVarOp.getIdentifier().getLessema()).append(", ");
                        else
                            generatedCode.append(actualType).append(" ").append(pVarOp.getIsRef().getReference() ? "*" : "").append(pVarOp.getIdentifier().getLessema()).append(", ");

                    }

                }

                if(!defDeclOp.getParDeclOps().isEmpty())
                    generatedCode.setLength(generatedCode.length() - 2);

                generatedCode.append(")\n");

                generatedCode.append("{\n");

                defDeclOp.getBodyOp().accept(this);

                generatedCode.append("}\n");

                break;
            }

            case Constants.IF_THEN_ELSE_OP: {

                IfThenElseOp ifThenElseOp = (IfThenElseOp) node;

                generatedCode.append("if(");

                ifThenElseOp.getExprOp().accept(this);

                generatedCode.append(")").append("{\n");

                ifThenElseOp.getThenBody().accept(this);

                generatedCode.append("} else {\n");

                ifThenElseOp.getElseBody().accept(this);

                generatedCode.append("}\n");

                break;
            }

            case Constants.IF_THEN_OP: {

                IfThenOp ifThenOp = (IfThenOp) node;

                generatedCode.append("if(");

                ifThenOp.getExprOp().accept(this);

                generatedCode.append(")").append("{\n");

                ifThenOp.getThenBody().accept(this);

                generatedCode.append("}\n");

                break;
            }

            case Constants.WHILE_OP: {

                WhileOp whileOp = (WhileOp) node;

                generatedCode.append("while(");

                whileOp.getExprOp().accept(this);

                generatedCode.append(")\n").append("{\n");

                whileOp.getBodyOp().accept(this);

                generatedCode.append("}\n");

                break;
            }

            case Constants.ASSIGN_OP: {

                AssignOp assignOp = (AssignOp) node;

                ArrayList<Visitable> exprOps = assignOp.getExprOps();

                ArrayList<Identifier> identifiers = assignOp.getIdentifiers();

                if(exprOps.size() == identifiers.size()) {

                    for (int i = 0; i < identifiers.size(); i++) {

                        if((resolveType(exprOps.get(i)).equals(Constants.STRINGC)) || (resolveType(exprOps.get(i)).equals(Constants.STRINGV))){

                            generatedCode.append("strcpy(");
                            generatedCode.append(identifiers.get(i).getLessema());
                            generatedCode.append(", ");
                            exprOps.get(i).accept(this);
                            generatedCode.append(");\n");

                        } else {

                            identifiers.get(i).accept(this);
                            generatedCode.append(" = ");
                            exprOps.get(i).accept(this);
                            generatedCode.append(";\n");

                        }

                    }

                } else {


                    for (int i = 0; i < identifiers.size(); i++) {

                        if((resolveType(exprOps.get(0)).equals(Constants.STRINGC)) || (resolveType(exprOps.get(0)).equals(Constants.STRINGV))){

                            generatedCode.append("strcpy(");
                            identifiers.get(i).accept(this);
                            generatedCode.append(", ");
                            exprOps.get(0).accept(this);
                            generatedCode.append(");\n");

                        } else {

                            identifiers.get(i).accept(this);
                            generatedCode.append(" = ");
                            exprOps.get(0).accept(this);
                            generatedCode.append(";\n");

                        }

                    }

                }

                break;
            }

            case Constants.WRITE_OP: {
                WriteOp writeOp = (WriteOp) node;

                ArrayList<Visitable> exprOps = writeOp.getExprOps();

                boolean hasNewLine = writeOp.getHasNewLine();

                for (int i = 0; i < exprOps.size(); i++) {
                    Visitable expr = exprOps.get(i);

                    if (expr instanceof Identifier) {
                        Identifier identifier = (Identifier) expr;

                        switch (identifier.getType().toString()) {
                            case Constants.ENUM_INT: {
                                if(hasNewLine)
                                    generatedCode.append("printf(\"%d\\n\", ").append(identifier.getLessema()).append(");\n");
                                else
                                    generatedCode.append("printf(\"%d\", ").append(identifier.getLessema()).append(");\n");
                                break;
                            }
                            case Constants.ENUM_BOOL: {
                                if(hasNewLine)
                                    generatedCode.append("printf(\"%s\\n\", ").append(identifier.getLessema()).append(" ? \"true\" : \"false\");\n");
                                else
                                    generatedCode.append("printf(\"%s\", ").append(identifier.getLessema()).append(" ? \"true\" : \"false\");\n");
                                break;
                            }
                            case Constants.ENUM_DOUBLE: {
                                if(hasNewLine)
                                    generatedCode.append("printf(\"%lf\\n\", ").append(identifier.getLessema()).append(");\n");
                                else
                                    generatedCode.append("printf(\"%lf\", ").append(identifier.getLessema()).append(");\n");
                                break;
                            }
                            case Constants.ENUM_CHAR: {
                                if(hasNewLine)
                                    generatedCode.append("printf(\"%c\\n\", ").append(identifier.getLessema()).append(");\n");
                                else
                                    generatedCode.append("printf(\"%c\", ").append(identifier.getLessema()).append(");\n");
                                break;
                            }
                            case Constants.ENUM_STRING:{
                                if(hasNewLine)
                                    generatedCode.append("printf(\"%s\\n\", ").append(identifier.getLessema()).append(");\n");
                                else
                                    generatedCode.append("printf(\"%s\", ").append(identifier.getLessema()).append(");\n");
                                break;
                            }
                        }
                    } else if (expr instanceof Const) {
                        Const constant = (Const) expr;

                        switch (resolveType(expr)) {
                            case Constants.ENUM_INT: {
                                if(hasNewLine)
                                    generatedCode.append("printf(\"%d\\n\", ").append(constant.getLessema()).append(");\n");
                                else
                                    generatedCode.append("printf(\"%d\", ").append(constant.getLessema()).append(");\n");
                                break;
                            }
                            case Constants.ENUM_BOOL: {
                                String boolValue = (Boolean.parseBoolean(constant.getLessema().toString())) ? "\"true\"" : "\"false\"";
                                if(hasNewLine)
                                    generatedCode.append("printf(\"%s\\n\", \"").append(boolValue).append("\");\n");
                                else
                                    generatedCode.append("printf(\"%s\", \"").append(boolValue).append("\");\n");
                                break;
                            }
                            case Constants.ENUM_DOUBLE: {
                                if(hasNewLine)
                                    generatedCode.append("printf(\"%lf\\n\", ").append(constant.getLessema()).append(");\n");
                                else
                                    generatedCode.append("printf(\"%lf\", ").append(constant.getLessema()).append(");\n");
                                break;
                            }
                            case Constants.ENUM_CHAR: {
                                if(hasNewLine)
                                    generatedCode.append("printf(\"%s\\n\", ").append(constant.getLessema()).append(");\n");
                                else
                                    generatedCode.append("printf(\"%s\", ").append(constant.getLessema()).append(");\n");
                                break;
                            }
                            case Constants.STRINGC: {
                                if(hasNewLine)
                                    generatedCode.append("printf(\"%s\\n\", ").append(constant.getLessema()).append(");\n");
                                else
                                    generatedCode.append("printf(\"%s\", ").append(constant.getLessema()).append(");\n");
                                break;
                            }
                        }
                    } else if (expr instanceof FunCall) {

                        FunCall funCall = (FunCall) expr;

                        switch (resolveType(expr)) {
                            case Constants.INT: {
                                if(hasNewLine) {
                                    generatedCode.append("printf(\"%d\\n\", ");
                                    funCall.accept(this);
                                    generatedCode.append(");\n");
                                } else {
                                    generatedCode.append("printf(\"%d\", ");
                                    funCall.accept(this);
                                    generatedCode.append(");\n");
                                }
                                break;
                            }
                            case Constants.BOOL: {
                                if(hasNewLine) {
                                    generatedCode.append("printf(\"%s\\n\", ");
                                    funCall.accept(this);
                                    generatedCode.append(");\n");
                                } else {
                                    generatedCode.append("printf(\"%s\", ");
                                    funCall.accept(this);
                                    generatedCode.append(");\n");
                                }
                                break;
                            }
                            case Constants.DOUBLE: {
                                if(hasNewLine) {
                                    generatedCode.append("printf(\"%lf\\n\", ");
                                    funCall.accept(this);
                                    generatedCode.append(");\n");
                                } else {
                                    generatedCode.append("printf(\"%lf\", ");
                                    funCall.accept(this);
                                    generatedCode.append(");\n");
                                }
                                break;
                            }
                            case Constants.CHAR: {
                                if(hasNewLine) {
                                    generatedCode.append("printf(\"%s\\n\", ");
                                    funCall.accept(this);
                                    generatedCode.append(");\n");
                                } else {
                                    generatedCode.append("printf(\"%s\", ");
                                    funCall.accept(this);
                                    generatedCode.append(");\n");
                                }
                                break;
                            }
                            case Constants.STRING, Constants.STRINGC, Constants.STRINGV : {
                                if(hasNewLine) {
                                    generatedCode.append("printf(\"%s\\n\", ");
                                    funCall.accept(this);
                                    generatedCode.append(");\n");
                                } else {
                                    generatedCode.append("printf(\"%s\", ");
                                    funCall.accept(this);
                                    generatedCode.append(");\n");
                                }
                                break;
                            }
                        }


                    } else if (expr instanceof ExprOp) {

                        ExprOp exprOp = (ExprOp) expr;

                        switch (resolveType(expr)) {
                            case Constants.INT: {
                                if(hasNewLine) {
                                    generatedCode.append("printf(\"%d\\n\", ");
                                    exprOp.accept(this);
                                    generatedCode.append(");\n");
                                } else {
                                    generatedCode.append("printf(\"%d\", ");
                                    exprOp.accept(this);
                                    generatedCode.append(");\n");
                                }
                                break;
                            }
                            case Constants.BOOL: {
                                if(hasNewLine) {
                                    generatedCode.append("printf(\"%s\\n\", ");
                                    exprOp.accept(this);
                                    generatedCode.append(");\n");
                                } else {
                                    generatedCode.append("printf(\"%s\", ");
                                    exprOp.accept(this);
                                    generatedCode.append(");\n");
                                }
                                break;
                            }
                            case Constants.DOUBLE: {
                                if(hasNewLine) {
                                    generatedCode.append("printf(\"%lf\\n\", ");
                                    exprOp.accept(this);
                                    generatedCode.append(");\n");
                                } else {
                                    generatedCode.append("printf(\"%lf\", ");
                                    exprOp.accept(this);
                                    generatedCode.append(");\n");
                                }
                                break;
                            }
                            case Constants.CHAR: {
                                if(hasNewLine) {
                                    generatedCode.append("printf(\"%s\\n\", ");
                                    exprOp.accept(this);
                                    generatedCode.append(");\n");
                                } else {
                                    generatedCode.append("printf(\"%s\", ");
                                    exprOp.accept(this);
                                    generatedCode.append(");\n");
                                }
                                break;
                            }
                            case Constants.STRING, Constants.STRINGC, Constants.STRINGV : {
                                if(hasNewLine) {
                                    generatedCode.append("printf(\"%s\\n\", ");
                                    exprOp.accept(this);
                                    generatedCode.append(");\n");
                                } else {
                                    generatedCode.append("printf(\"%s\", ");
                                    exprOp.accept(this);
                                    generatedCode.append(");\n");
                                }
                                break;
                            }
                        }
                    } else {
                        if(hasNewLine) {
                            generatedCode.append("printf(\"%s\\n\", ");
                            expr.accept(this); // Per gestire altre espressioni
                            generatedCode.append(");\n");
                        } else {
                            generatedCode.append("printf(\"%s\", ");
                            expr.accept(this); // Per gestire altre espressioni
                            generatedCode.append(");\n");
                        }

                    }
                }

                break;
            }

            case Constants.READ_OP: {
                ReadOp readOp = (ReadOp) node;

                ArrayList<Identifier> identifiers = readOp.getIdentifiers();

                for (Identifier identifier : identifiers) {
                    generatedCode.append("scanf(");

                    switch (identifier.getType().toString()) {
                        case Constants.ENUM_INT: {
                            generatedCode.append("\"%d\", &").append(identifier.getLessema()).append(");");
                            break;
                        }
                        case Constants.ENUM_BOOL: {
                            generatedCode.append("\"%d\", &temp_bool); "); // Legge come intero temporaneo
                            generatedCode.append(identifier.getLessema()).append(" = (temp_bool == 1);"); // Conversione a booleano
                            break;
                        }
                        case Constants.ENUM_DOUBLE: {
                            generatedCode.append("\"%lf\", &").append(identifier.getLessema()).append(");");
                            break;
                        }
                        case Constants.ENUM_CHAR: {
                            generatedCode.append("\" %c\", &").append(identifier.getLessema()).append(");");
                            break;
                        }
                        case Constants.ENUM_STRING: {
                            generatedCode.append("\"%s\", ").append(identifier.getLessema()).append(");");
                            break;
                        }
                        default: {
                            generatedCode.append("/* Tipo non supportato: ").append(identifier.getType().toString()).append(" */");
                            break;
                        }
                    }

                    generatedCode.append("\n"); // Aggiunge una nuova riga per leggibilità
                }

                break;
            }

            case Constants.FUN_CALL: {

                FunCall funCall = (FunCall) node;

                String name = funCall.getIdentifier().getLessema();


                generatedCode.append(Constants.FUNCTION_PREFIX).append(name).append("(");

                ArrayList<String> keys = new ArrayList<>(funCall.getInputParameters().keySet());

                for(int i = 0; i < funCall.getInputParameters().size(); i++) {

                    String type = funCall.getInputParameters().get(keys.get(i)).toString();

                    if(type.contains("REF_")) {

                        if(resolveType(funCall.getExprOps().get(i)).equals(Constants.STRINGV)) {
                            funCall.getExprOps().get(i).accept(this);
                            generatedCode.append(", ");
                        } else {
                            generatedCode.append("&");
                            funCall.getExprOps().get(i).accept(this);
                            generatedCode.append(", ");
                        }

                    } else {

                        funCall.getExprOps().get(i).accept(this);
                        generatedCode.append(", ");

                    }

                }

                if(funCall.getInputParameters().size() > 0)
                    generatedCode.setLength(generatedCode.length() - 2);

                generatedCode.append(")");


                break;
            }


            case Constants.FUN_CALL_OP: {

                FunCallOp funCallOp = (FunCallOp) node;

                String name = funCallOp.getFuncall().getIdentifier().getLessema();


                generatedCode.append(Constants.FUNCTION_PREFIX).append(name).append("(");

                ArrayList<String> keys = new ArrayList<>(funCallOp.getFuncall().getInputParameters().keySet());

                for(int i = 0; i < funCallOp.getFuncall().getInputParameters().size(); i++) {

                    String type = funCallOp.getFuncall().getInputParameters().get(keys.get(i)).toString();

                    if(type.contains("REF_")) {

                        String exprType = ((Identifier) funCallOp.getFuncall().getExprOps().get(i)).getType().toString();

                        if(!exprType.contains("REF_") && !exprType.equals(Constants.ENUM_STRING)) {
                            generatedCode.append("&");
                            funCallOp.getFuncall().getExprOps().get(i).accept(this);
                        } else {
                            printRefRef(funCallOp.getFuncall().getExprOps().get(i));
                        }

                        generatedCode.append(", ");

                    } else {

                        funCallOp.getFuncall().getExprOps().get(i).accept(this);
                        generatedCode.append(", ");

                    }

                }

                if(!keys.isEmpty())
                    generatedCode.setLength(generatedCode.length() - 2);

                generatedCode.append(");\n");


                break;
            }

            case Constants.ADD_OP: {
                AddOp addOp = (AddOp) node;
                Visitable ex1 = addOp.getExpr1();
                Visitable ex2 = addOp.getExpr2();

                String typeEx1 = resolveType(ex1);
                String typeEx2 = resolveType(ex2);

                // Se almeno uno dei due tipi è STRINGC o STRINGV, gestiamo come concatenazione
                if ((typeEx1.equals(Constants.STRINGC) || typeEx1.equals(Constants.STRINGV)) && !(typeEx2.equals(Constants.STRINGC) || typeEx2.equals(Constants.STRINGV))) {

                    String typeToConf = typeEx2;

                    switch (typeToConf) {

                        case Constants.INT: {

                            generatedCode.append("str_concat(");
                            ex1.accept(this);
                            generatedCode.append(", ");
                            generatedCode.append("integer_to_str(");
                            ex2.accept(this); // Valore della prima parte della concatenazione
                            generatedCode.append("))");

                            break;
                        }

                        case Constants.DOUBLE: {

                            generatedCode.append("str_concat(");
                            ex1.accept(this);
                            generatedCode.append(", ");
                            generatedCode.append("double_to_str(");
                            ex2.accept(this); // Valore della prima parte della concatenazione
                            generatedCode.append("))");

                            break;
                        }

                        case Constants.BOOL: {

                            generatedCode.append("str_concat(");
                            ex1.accept(this);
                            generatedCode.append(", ");
                            generatedCode.append("bool_to_str(");
                            ex2.accept(this); // Valore della prima parte della concatenazione
                            generatedCode.append("))");

                            break;
                        }

                        case Constants.CHAR: {

                            generatedCode.append("str_concat(");
                            ex1.accept(this);
                            generatedCode.append(", ");
                            generatedCode.append("char_to_str(");
                            ex2.accept(this); // Valore della prima parte della concatenazione
                            generatedCode.append("))");


                            break;
                        }


                    }

                } else if ((typeEx2.equals(Constants.STRINGC) || typeEx2.equals(Constants.STRINGV)) && !(typeEx1.equals(Constants.STRINGC) || typeEx1.equals(Constants.STRINGV))) {

                    String typeToConf = typeEx1;

                    switch (typeToConf) {

                        case Constants.INT: {

                            generatedCode.append("str_concat(" + "integer_to_str(");
                            ex1.accept(this); // Valore della prima parte della concatenazione
                            generatedCode.append("), ");
                            ex2.accept(this);
                            generatedCode.append(")");

                            break;
                        }

                        case Constants.DOUBLE: {

                            generatedCode.append("str_concat(" + "double_to_str(");
                            ex1.accept(this); // Valore della prima parte della concatenazione
                            generatedCode.append("), ");
                            ex2.accept(this);
                            generatedCode.append(")");

                            break;
                        }

                        case Constants.BOOL: {

                            generatedCode.append("str_concat(" + "bool_to_str(");
                            ex1.accept(this); // Valore della prima parte della concatenazione
                            generatedCode.append("), ");
                            ex2.accept(this);
                            generatedCode.append(")");

                            break;
                        }

                        case Constants.CHAR: {

                            generatedCode.append("str_concat(" + "char_to_str(");
                            ex1.accept(this); // Valore della prima parte della concatenazione
                            generatedCode.append("), ");
                            ex2.accept(this);
                            generatedCode.append(")");

                            break;
                        }

                    }


                } else if ((typeEx2.equals(Constants.STRINGC) || typeEx2.equals(Constants.STRINGV)) && (typeEx1.equals(Constants.STRINGC) || typeEx1.equals(Constants.STRINGV))) {

                    // Gestione della concatenazione nidificata
                    generatedCode.append("str_concat(");
                    ex1.accept(this); // Valore della prima parte della concatenazione
                    generatedCode.append(", ");

                    if (ex2 instanceof AddOp) {
                        // Ricorsione per concatenazioni multiple
                        generatedCode.append("(");
                        ex2.accept(this);
                        generatedCode.append(")");
                    } else {
                        ex2.accept(this); // Valore della seconda parte
                    }
                    generatedCode.append(")");

                } else {
                    // Operazioni numeriche classiche
                    ex1.accept(this);
                    generatedCode.append(" + ");
                    ex2.accept(this);
                }


                break;
            }

            case Constants.DIV_OP: {
                DivOp divOp = (DivOp) node;
                Visitable ex1 = divOp.getExpr1();
                Visitable ex2 = divOp.getExpr2();
                ex1.accept(this);
                generatedCode.append(" / ");
                ex2.accept(this);
                break;
            }

            case Constants.TIMES_OP: {
                TimesOp timesOp = (TimesOp) node;
                Visitable ex1 = timesOp.getExpr1();
                Visitable ex2 = timesOp.getExpr2();
                ex1.accept(this);
                generatedCode.append(" * ");
                ex2.accept(this);
                break;
            }

            case Constants.MINUS_OP: {
                MinusOp minusOp = (MinusOp) node;
                Visitable ex1 = minusOp.getExpr1();
                Visitable ex2 = minusOp.getExpr2();
                ex1.accept(this);
                generatedCode.append(" - ");
                ex2.accept(this);
                break;
            }

            case Constants.NOT_OP: {
                NotOp notOp = (NotOp) node;
                Visitable expr = notOp.getExpr1();
                generatedCode.append("!(");
                expr.accept(this);
                generatedCode.append(")");
                break;
            }

            case Constants.UMINUS_OP: {
                UminusOp uminusOp = (UminusOp) node;
                Visitable expr = uminusOp.getExpr1();
                generatedCode.append("-");
                expr.accept(this);
                break;
             }

            case Constants.AND_OP: {
                AndOp andOp = (AndOp) node;
                Visitable ex1 = andOp.getExpr1();
                Visitable ex2 = andOp.getExpr2();
                ex1.accept(this);
                generatedCode.append(" && ");
                ex2.accept(this);
                break;
            }

            case Constants.OR_OP: {
                OrOp orOp = (OrOp) node;
                Visitable ex1 = orOp.getExpr1();
                Visitable ex2 = orOp.getExpr2();
                ex1.accept(this);
                generatedCode.append(" || ");
                ex2.accept(this);
                break;
            }

            case Constants.EQ_OP: {
                EqOp eqOp = (EqOp) node;
                Visitable ex1 = eqOp.getExpr1();
                Visitable ex2 = eqOp.getExpr2();

                // Risolvi i tipi di entrambi gli operandi
                String typeEx1 = resolveType(ex1);
                String typeEx2 = resolveType(ex2);

                if (typeEx1.equals(Constants.STRINGC) || typeEx1.equals(Constants.STRINGV) ||
                        typeEx2.equals(Constants.STRINGC) || typeEx2.equals(Constants.STRINGV)) {
                    // Confronto tra stringhe usando strcmp
                    generatedCode.append("(strcmp(");
                    ex1.accept(this); // Genera il codice per il primo operando
                    generatedCode.append(", ");
                    ex2.accept(this); // Genera il codice per il secondo operando
                    generatedCode.append(") == 0)");
                } else {
                    // Confronto normale per tipi non stringa
                    ex1.accept(this);
                    generatedCode.append(" == ");
                    ex2.accept(this);
                }
                break;
            }

            case Constants.GE_OP: {
                GeOp geOp = (GeOp) node;
                Visitable ex1 = geOp.getExpr1();
                Visitable ex2 = geOp.getExpr2();
                ex1.accept(this);
                generatedCode.append(" >= ");
                ex2.accept(this);
                break;
            }

            case Constants.GT_OP: {
                GtOp gtOp = (GtOp) node;
                Visitable ex1 = gtOp.getExpr1();
                Visitable ex2 = gtOp.getExpr2();
                ex1.accept(this);
                generatedCode.append(" > ");
                ex2.accept(this);
                break;
            }

            case Constants.LE_OP: {
                LeOp leOp = (LeOp) node;
                Visitable ex1 = leOp.getExpr1();
                Visitable ex2 = leOp.getExpr2();
                ex1.accept(this);
                generatedCode.append(" <= ");
                ex2.accept(this);
                break;
            }

            case Constants.LT_OP: {
                LtOp ltOp = (LtOp) node;
                Visitable ex1 = ltOp.getExpr1();
                Visitable ex2 = ltOp.getExpr2();
                ex1.accept(this);
                generatedCode.append(" < ");
                ex2.accept(this);
                break;
            }

            case Constants.NE_OP: {
                NeOp neOp = (NeOp) node;
                Visitable ex1 = neOp.getExpr1();
                Visitable ex2 = neOp.getExpr2();

                String typeEx1 = resolveType(ex1);
                String typeEx2 = resolveType(ex2);

                if (typeEx1.equals(Constants.STRINGC) || typeEx1.equals(Constants.STRINGV) ||
                        typeEx2.equals(Constants.STRINGC) || typeEx2.equals(Constants.STRINGV)) {
                    generatedCode.append("(strcmp(");
                    ex1.accept(this);
                    generatedCode.append(", ");
                    ex2.accept(this);
                    generatedCode.append(") != 0)");
                } else {
                    // Confronto normale per tipi non stringa
                    ex1.accept(this);
                    generatedCode.append(" != ");
                    ex2.accept(this);
                }
                break;
            }

            case Constants.RETURN_OP: {

                ReturnOp returnOp = (ReturnOp) node;

                generatedCode.append("return ");

                returnOp.getExprOp().accept(this);

                generatedCode.append(";\n");

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

    private void visitBeginEndOp(Node node, boolean isABeginEndOp) {
        List<Visitable> children = node.getChildren();
        if (children != null && !children.isEmpty()) {
            for (Visitable child : children) {
                if (child != null) {
                    if(child.getClass().getSimpleName().equals(Constants.LET_OP)) {

                        StringBuilder generatedCodeForFunctions = new StringBuilder();

                        for(Symbol symbol : ((LetOp)child).getSymbolTable().getSymbols()) {

                            if(symbol.getKind().equals(Kind.PROCEDURE) || symbol.getKind().equals(Kind.FUNCTION)) {

                                generatedCodeForFunctions.append(fromEnumToType(symbol.getTypeSym().getTypeEnum())).append(" ");
                                generatedCodeForFunctions.append(Constants.FUNCTION_PREFIX).append(symbol.getSymbol()).append("(");
                                Set<String> keys = symbol.getTypeSym().getInputParameters().keySet();
                                for(String key : keys) {

                                    generatedCodeForFunctions.append(fromEnumToType(symbol.getTypeSym().getInputParameters().get(key)));
                                    generatedCodeForFunctions.append(key).append(", ");

                                }

                                if(!keys.isEmpty())
                                    generatedCodeForFunctions.setLength(generatedCodeForFunctions.length() - 2);
                                generatedCodeForFunctions.append(");\n");

                                generatedCode = new StringBuilder(generatedCode.toString().replace("//placeholderForLetOpFunctions\n", generatedCodeForFunctions.toString()));


                            }

                        }

                    }
                }
            }

        }

        if(isABeginEndOp)
            generatedCode.append("int main() {\n");
        else
            generatedCode.append("{\n");

        children = node.getChildren();
        if (children != null && !children.isEmpty()) {
            for (Visitable child : children) {
                if (child != null) {
                    child.accept(this);
                }
            }

        }

        generatedCode.append("}\n");


    }

    @Override
    public void visit(Leaf node) {

        switch (node.getClass().getSimpleName()) {

            case Constants.IDENTIFIER: {
                Identifier identifier = (Identifier) node;
                if (identifier.getType() != null && identifier.getType().toString().contains("REF_")) {
                    generatedCode.append("*").append(identifier.getLessema());
                } else {
                    generatedCode.append(identifier.getLessema());
                }

                break;
            }

            case Constants.INT_CONST: {

                IntConst intConst = (IntConst) node;

                generatedCode.append(intConst.getLessema());

                break;
            }

            case Constants.DOUBLE_CONST: {
                DoubleConst doubleConst = (DoubleConst) node;

                generatedCode.append(doubleConst.getLessema());

                break;
            }

            case Constants.STRING_CONST: {
                StringConst stringConst = (StringConst) node;

                generatedCode.append(stringConst.getLessema());

                break;
            }

            case Constants.CHAR_CONST: {
                CharConst charConst = (CharConst) node;

                generatedCode.append("'" + charConst.getLessema() + "'");

                
                break;
            }

            case Constants.BOOL_CONST: {

                BoolConst boolConst = (BoolConst) node;

                generatedCode.append(boolConst.getLessema());

                break;
            }
        }

    }

    public void printRefRef(Visitable node) {

        Identifier identifier = (Identifier) node;

        generatedCode.append(identifier.getLessema());

    }

    public StringBuilder getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(StringBuilder generatedCode) {
        this.generatedCode = generatedCode;
    }

    private String resolveType(Visitable expr) {
        switch (expr.getClass().getSimpleName()) {
            case Constants.IDENTIFIER:
                return resolveType(((Identifier) expr).getType());
            case Constants.INT_CONST, Constants.REF_INT:
                return resolveType(TypeEnum.INT);
            case Constants.DOUBLE_CONST, Constants.REF_DOUBLE:
                return resolveType(TypeEnum.DOUBLE);
            case Constants.BOOL_CONST, Constants.REF_BOOL:
                return resolveType(TypeEnum.BOOL);
            case Constants.CHAR_CONST, Constants.REF_CHAR:
                return resolveType(TypeEnum.CHAR);
            case Constants.STRING_CONST, Constants.REF_STRING:
                return resolveType(TypeEnum.STRING);
            default:
                return resolveType(((ExprOp) expr).getType());
        }
    }

    public String resolveType (TypeEnum typeEnum) {

        if(typeEnum.toString().equals(TypeEnum.INT.toString()) || typeEnum.toString().equals(TypeEnum.REF_INT.toString()))
            return Constants.INT;
        if(typeEnum.toString().equals(TypeEnum.DOUBLE.toString()) || typeEnum.toString().equals(TypeEnum.REF_DOUBLE.toString()))
            return Constants.DOUBLE;
        if(typeEnum.toString().equals(TypeEnum.CHAR.toString()) || typeEnum.toString().equals(TypeEnum.REF_CHAR.toString()))
            return Constants.CHAR;
        if(typeEnum.toString().equals(TypeEnum.STRING.toString()) || typeEnum.toString().equals(TypeEnum.REF_STRING.toString()))
            return Constants.STRINGC;
        if(typeEnum.toString().equals(TypeEnum.BOOL.toString()) || typeEnum.toString().equals(TypeEnum.REF_BOOL.toString()))
            return Constants.BOOL;
        return Constants.VOID;
    }

    public String fromEnumToType (TypeEnum typeEnum){

        switch (typeEnum) {

            case BOOL:
                return Constants.BOOL + " ";
            case REF_BOOL:
                return Constants.BOOL + " *";
            case INT:
                return Constants.INT + " ";
            case REF_INT:
                return Constants.INT + " *";
            case STRING, REF_STRING:
                return Constants.STRINGC + " ";
            case DOUBLE:
                return Constants.DOUBLE + " ";
            case REF_DOUBLE:
                return Constants.DOUBLE + " *";
            case CHAR:
                return Constants.CHAR + " ";
            case REF_CHAR:
                return Constants.CHAR + " *";
        }

        return Constants.VOID;
    }

}
