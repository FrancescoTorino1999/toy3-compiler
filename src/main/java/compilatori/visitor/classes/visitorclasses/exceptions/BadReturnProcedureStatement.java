package compilatori.visitor.classes.visitorclasses.exceptions;

public class BadReturnProcedureStatement extends Exception {

    // Costruttore con messaggio predefinito
    public BadReturnProcedureStatement() {
        super("Le procedure non devono avere return");
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con messaggio personalizzato
    public BadReturnProcedureStatement(String message, Throwable cause) {
        super(message, cause);
        System.err.println(getMessage());
        System.exit(0);
    }

    // Costruttore con causa
    public BadReturnProcedureStatement(Throwable cause) {
        super(cause);
        System.err.println(getMessage());
        System.exit(0);
    }
}