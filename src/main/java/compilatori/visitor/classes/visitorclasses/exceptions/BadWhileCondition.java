package compilatori.visitor.classes.visitorclasses.exceptions;

public class BadWhileCondition extends Exception {

    // Costruttore con messaggio predefinito
    public BadWhileCondition() {
        super("La condizione del while deve essere booleana");
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con messaggio personalizzato
    public BadWhileCondition(String message, Throwable cause) {
        super(message, cause);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con causa
    public BadWhileCondition(Throwable cause) {
        super(cause);
        System.err.println(getMessage());
        System.exit(0);
    }
}