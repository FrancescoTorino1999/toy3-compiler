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
import compilatori.visitor.classes.visitorclasses.baseclasses.Leaf;
import compilatori.visitor.classes.visitorclasses.baseclasses.Node;
import compilatori.visitor.classes.visitorclasses.baseclasses.TypeEnum;
import compilatori.visitor.classes.visitorclasses.baseclasses.Visitable;

import java.util.ArrayList;
import java.util.List;

public class CodeGeneratorUtils {
    public static void addHelperFunctions(StringBuilder appendr) {
        try {
            appendr.append("char* integer_to_str(int i){\n");
            appendr.append("\tint length= snprintf(NULL,0,\"%d\",i);\n");
            appendr.append("\tchar* result=malloc(length+1);\n");
            appendr.append("\tsnprintf(result,length+1,\"%d\",i);\n");
            appendr.append("\treturn result;\n");
            appendr.append("}\n");

            appendr.append("char* double_to_str(float i){\n");
            appendr.append("\tint length= snprintf(NULL,0,\"%f\",i);\n");
            appendr.append("\tchar* result=malloc(length+1);\n");
            appendr.append("\tsnprintf(result,length+1,\"%f\",i);\n");
            appendr.append("\treturn result;\n");
            appendr.append("}\n");

            appendr.append("char* char_to_str(char i){\n");
            appendr.append("\tint length= snprintf(NULL,0,\"%c\",i);\n");
            appendr.append("\tchar* result=malloc(length+1);\n");
            appendr.append("\tsnprintf(result,length+1,\"%c\",i);\n");
            appendr.append("\treturn result;\n");
            appendr.append("}\n");

            appendr.append("char* bool_to_str(bool i){\n");
            appendr.append("\tint length= snprintf(NULL,0,\"%d\",i);\n");
            appendr.append("\tchar* result=malloc(length+1);\n");
            appendr.append("\tsnprintf(result,length+1,\"%d\",i);\n");
            appendr.append("\treturn result;\n");
            appendr.append("}\n");

            appendr.append("char* str_concat(char* str1, char* str2){\n");
            appendr.append("\tchar* result=malloc(sizeof(char)*MAXSIZE);\n");
            appendr.append("\tresult=strcat(result,str1);\n");
            appendr.append("\tresult=strcat(result,str2);\n");
            appendr.append("\treturn result;\n}\n");

            appendr.append("\n");
            appendr.append("char* read_str(){\n");
            appendr.append("\tchar* str=malloc(sizeof(char)*MAXSIZE);\n");
            appendr.append("\tscanf(\"%s\",str);\n");
            appendr.append("\treturn str;\n}\n");

            appendr.append("\n");
            appendr.append("int str_to_bool(char* expr){\n");
            appendr.append("\tint i=0;\n");
            appendr.append("\tif ( (strcmp(expr, \"true\")==0) || (strcmp(expr, \"1\"))==0 )\n");
            appendr.append("\t\ti=1;\n");
            appendr.append("\tif ( (strcmp(expr, \"false\")==0) || (strcmp(expr, \"0\"))==0 )\n");
            appendr.append("\t\ti=0;\n");
            appendr.append("\treturn i;\n}\n");

            appendr.append("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
