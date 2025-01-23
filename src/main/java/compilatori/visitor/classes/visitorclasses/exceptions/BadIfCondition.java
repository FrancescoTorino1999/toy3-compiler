package compilatori.visitor.classes.visitorclasses.exceptions;

public class BadIfCondition extends Exception {

    // Costruttore con messaggio predefinito
    public BadIfCondition() {
        super("La condizione dell'if deve essere booleana");
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con messaggio personalizzato
    public BadIfCondition(String message, Throwable cause) {
        super(message, cause);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con causa
    public BadIfCondition(Throwable cause) {
        super(cause);
        System.err.println(getMessage());
        System.exit(0);
    }
}