package es.udc.psi.cleanarchv2.app.data.network;

/**
 * Tells the repositories whether the remote sources can be reached.
 * It is an interface so that the repositories do not depend on Android
 * and the tests can simulate a device with or without network.
 */
public interface NetworkChecker {

    boolean isNetworkAvailable();
}
