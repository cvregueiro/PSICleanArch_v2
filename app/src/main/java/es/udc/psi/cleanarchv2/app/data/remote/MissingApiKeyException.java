package es.udc.psi.cleanarchv2.app.data.remote;

import java.io.IOException;

/**
 * The setlist.fm API key has not been configured in local.properties.
 */
public class MissingApiKeyException extends IOException {

    public MissingApiKeyException() {

        super("setlist.fm API key not found in local.properties");
    }
}
