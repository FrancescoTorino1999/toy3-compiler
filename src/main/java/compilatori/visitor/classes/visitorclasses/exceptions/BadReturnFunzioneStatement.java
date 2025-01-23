package compilatori.visitor.classes.visitorclasses.exceptions;

public class BadReturnFunzioneStatement extends Exception {

    // Costruttore con messaggio predefinito
    public BadReturnFunzioneStatement() {
        super("Le funzioni devono avere almeno return del tipo della funzione");
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con messaggio personalizzato
    public BadReturnFunzioneStatement(String message, Throwable cause) {
        super(message, cause);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con causa
    public BadReturnFunzioneStatement(Throwable cause) {
        super(cause);
        System.err.println(getMessage());
        System.exit(0);
    }
}