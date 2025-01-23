package compilatori.visitor.classes.visitorclasses.exceptions;

public class BadAssignment extends Exception {

    // Costruttore con messaggio predefinito
    public BadAssignment() {
        super("Errore assegnazione errata");
        System.err.println(getMessage());
        System.exit(0);
    }

    public BadAssignment(String symbol) {
        super("Errore assegnazione errata della variabile " + symbol);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con messaggio personalizzato
    public BadAssignment(String message, Throwable cause) {
        super(message, cause);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con causa
    public BadAssignment(Throwable cause) {
        super(cause);
        System.err.println(getMessage());
        System.exit(0);
    }
}