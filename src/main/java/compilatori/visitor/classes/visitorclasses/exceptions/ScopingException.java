package compilatori.visitor.classes.visitorclasses.exceptions;

public class ScopingException extends Exception {

    // Costruttore con messaggio predefinito
    public ScopingException(String variableName) {
        super("Variabile già dichiarata nello scope corrente: " + variableName);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore senza argomenti (opzionale)
    public ScopingException() {
        super("Variabile già dichiarata nello scope corrente.");
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con messaggio personalizzato
    public ScopingException(String message, Throwable cause) {
        super(message, cause);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con causa
    public ScopingException(Throwable cause) {
        super(cause);
        System.err.println(getMessage());
        System.exit(0);
    }
}