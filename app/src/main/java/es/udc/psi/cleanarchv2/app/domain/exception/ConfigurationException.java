package es.udc.psi.cleanarchv2.app.domain.exception;

import java.io.IOException;

/**
 * The source of the data is not configured (for example, an API key is missing).
 */
public class ConfigurationException extends IOException {

    public ConfigurationException(String message) {

        super(message);
    }
}
