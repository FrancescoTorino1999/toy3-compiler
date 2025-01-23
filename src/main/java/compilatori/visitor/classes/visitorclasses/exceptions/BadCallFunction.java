package compilatori.visitor.classes.visitorclasses.exceptions;

public class BadCallFunction extends Exception {

    // Costruttore con messaggio predefinito
    public BadCallFunction() {
        super("Utilizza i parametri corretti per richiamare la funzione/procedura");
        System.err.println(getMessage());
        System.exit(0);
    }

    public BadCallFunction(String func) {
        super("Utilizza i parametri corretti per richiamare la funzione/procedura " + func);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con messaggio personalizzato
    public BadCallFunction(String message, Throwable cause) {
        super(message, cause);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con causa
    public BadCallFunction(Throwable cause) {
        super(cause);
        System.err.println(getMessage());
        System.exit(0);
    }
}