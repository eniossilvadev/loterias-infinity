package br.com.enio.silva.loterias.exception;

public class GeneralLotteryException extends RuntimeException {

    private static final long serialVersionUID = 6922866024211417498L;

    public GeneralLotteryException() {
        super();
    }

    public GeneralLotteryException(String message) {
        super(message);
    }

    public GeneralLotteryException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralLotteryException(Throwable cause) {
        super(cause);
    }

    protected GeneralLotteryException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
