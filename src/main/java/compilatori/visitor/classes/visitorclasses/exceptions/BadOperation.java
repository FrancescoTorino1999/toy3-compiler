package compilatori.visitor.classes.visitorclasses.exceptions;

public class BadOperation extends Exception {

    // Costruttore con messaggio predefinito
    public BadOperation(String op, String op1, String op2) {
        super("Bad operation: errore operazione applicata su tipi errati operazione " + op + " applicata sui tipi " + op1 + ", "+ op2);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore senza argomenti (opzionale)
    public BadOperation() {
        super("Errore operazione applicata su tipi errati operazione.");
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con messaggio personalizzato
    public BadOperation(String message, Throwable cause) {
        super(message, cause);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con causa
    public BadOperation(Throwable cause) {
        super(cause);
        System.err.println(getMessage());
        System.exit(0);
    }
}