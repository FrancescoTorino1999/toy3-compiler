package compilatori.visitor.classes.visitorclasses.exceptions;

public class FunctionNotDeclaredException extends Exception {

    // Costruttore con messaggio predefinito
    public FunctionNotDeclaredException(String variableName) {
        super("Funzione non dichiarata nello scope " + variableName);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore senza argomenti (opzionale)
    public FunctionNotDeclaredException() {
        super("Funzione non dichiarata.");
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con messaggio personalizzato
    public FunctionNotDeclaredException(String message, Throwable cause) {
        super(message, cause);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con causa
    public FunctionNotDeclaredException(Throwable cause) {
        super(cause);
        System.err.println(getMessage());
        System.exit(0);
    }
}