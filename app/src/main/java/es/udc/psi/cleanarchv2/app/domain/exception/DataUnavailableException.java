package es.udc.psi.cleanarchv2.app.domain.exception;

/**
 * The data needed by a use case could not be obtained (network down, server error...).
 * The outer layers translate their own errors (IOException, HTTP codes...) into this exception.
 */
public class DataUnavailableException extends Exception {

    public DataUnavailableException(String message) {

        super(message);
    }

    public DataUnavailableException(String message,
                                    Throwable cause) {

        super(message, cause);
    }
}
