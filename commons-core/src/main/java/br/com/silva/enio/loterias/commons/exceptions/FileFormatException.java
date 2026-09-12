package br.com.silva.enio.loterias.commons.exceptions;

/**
 * Exceção lançada quando o formato do arquivo é inválido ou inesperado.
 */
public class FileFormatException extends GeneralRuntimeException {

    private static final long serialVersionUID = 6247639215258763636L;

    /**
     * Construtor padrão.
     */
    public FileFormatException() {
        super();
    }

    /**
     * Construtor com mensagem de erro.
     *
     * @param message Detalhes sobre o erro de formato de arquivo
     */
    public FileFormatException(String message) {
        super(message);
    }

    /**
     * Construtor com mensagem de erro e causa original.
     *
     * @param message Detalhes sobre o erro de formato de arquivo
     * @param cause   Exceção original que causou este erro
     */
    public FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construtor com causa original.
     *
     * @param cause Exceção original que causou este erro
     */
    public FileFormatException(Throwable cause) {
        super(cause);
    }

    /**
     * Construtor avançado que permite habilitar supressão e controle de stack trace.
     */
    protected FileFormatException(String message, Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
