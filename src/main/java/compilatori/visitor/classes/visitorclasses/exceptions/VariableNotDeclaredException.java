package compilatori.visitor.classes.visitorclasses.exceptions;

public class VariableNotDeclaredException extends Exception {

    // Costruttore con messaggio predefinito
    public VariableNotDeclaredException(String variableName) {
        super("Variabile non dichiarata nello scope " + variableName);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore senza argomenti (opzionale)
    public VariableNotDeclaredException() {
        super("Variabile non dichiarata.");
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con messaggio personalizzato
    public VariableNotDeclaredException(String message, Throwable cause) {
        super(message, cause);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con causa
    public VariableNotDeclaredException(Throwable cause) {
        super(cause);
        System.err.println(getMessage());
        System.exit(0);
    }
}