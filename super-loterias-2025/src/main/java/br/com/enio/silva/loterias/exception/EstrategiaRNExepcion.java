package br.com.enio.silva.loterias.exception;

public class EstrategiaRNExepcion extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -4970264383202437337L;

	public EstrategiaRNExepcion() {
		super();
	}

	public EstrategiaRNExepcion(String message) {
		super(message);
	}

	public EstrategiaRNExepcion(String message, Throwable cause) {
		super(message, cause);
	}

	public EstrategiaRNExepcion(String message, Throwable cause, boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EstrategiaRNExepcion(Throwable cause) {
		super(cause);
	}

}
