package compilatori.visitor.classes.visitorclasses.exceptions;

public class TypeAssignmentException extends Exception {

    // Costruttore con messaggio predefinito
    public TypeAssignmentException(String variableName) {
        super("Assegnazione al tipo errato " + variableName);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore senza argomenti (opzionale)
    public TypeAssignmentException() {
        super("Variabile già dichiarata nello scope corrente.");
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con messaggio personalizzato
    public TypeAssignmentException(String message, Throwable cause) {
        super(message, cause);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con causa
    public TypeAssignmentException(Throwable cause) {
        super(cause);
        System.err.println(getMessage());
        System.exit(0);
    }
}