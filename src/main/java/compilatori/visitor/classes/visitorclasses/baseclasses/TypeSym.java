package compilatori.visitor.classes.visitorclasses.baseclasses;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class TypeSym {

    private boolean isFunction;
    private boolean isVariable;
    private TypeEnum typeEnum; // Tipo di ritorno della funzione o tipo variabile
    private LinkedHashMap<String, TypeEnum> inputParameters; // Parametri in formato chiave-valore

    public TypeSym(boolean isFunction, boolean isVariable, TypeEnum typeEnum, LinkedHashMap<String, TypeEnum> inputParameters) {
        this.isFunction = isFunction;
        this.isVariable = isVariable;
        this.typeEnum = typeEnum;
        this.inputParameters = inputParameters;
    }

    public boolean isFunction() {
        return isFunction;
    }

    public void setFunction(boolean function) {
        isFunction = function;
    }

    public boolean isVariable() {
        return isVariable;
    }

    public void setVariable(boolean variable) {
        isVariable = variable;
    }

    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public LinkedHashMap<String, TypeEnum> getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(LinkedHashMap<String, TypeEnum> inputParameters) {
        this.inputParameters = inputParameters;
    }


}
