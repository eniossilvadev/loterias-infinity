package br.com.silva.enio.loterias.commons.exceptions;

public class GeneralRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 6922866024211417498L;

    public GeneralRuntimeException() {
        super();
    }

    public GeneralRuntimeException(String message) {
        super(message);
    }

    public GeneralRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralRuntimeException(Throwable cause) {
        super(cause);
    }

    protected GeneralRuntimeException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
