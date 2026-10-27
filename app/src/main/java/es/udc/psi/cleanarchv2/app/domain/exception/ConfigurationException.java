package es.udc.psi.cleanarchv2.app.domain.exception;

/**
 * The data cannot be obtained because the application is not configured to access it.
 */
public class ConfigurationException extends DataUnavailableException {

    public ConfigurationException(String message) {

        super(message);
    }
}
