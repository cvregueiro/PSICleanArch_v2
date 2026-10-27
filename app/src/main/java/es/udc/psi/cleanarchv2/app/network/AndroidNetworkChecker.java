package es.udc.psi.cleanarchv2.app.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

import es.udc.psi.cleanarchv2.data.network.NetworkChecker;

/**
 * Checks the connectivity with the ConnectivityManager of Android
 * (needs the ACCESS_NETWORK_STATE permission).
 */
public class AndroidNetworkChecker implements NetworkChecker {

    private final ConnectivityManager mConnectivityManager;

    public AndroidNetworkChecker(Context context) {

        mConnectivityManager = context.getSystemService(ConnectivityManager.class);
    }

    @Override
    public boolean isNetworkAvailable() {

        Network network = mConnectivityManager.getActiveNetwork();
        if (network == null) {
            return false;
        }

        NetworkCapabilities capabilities = mConnectivityManager.getNetworkCapabilities(network);
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }
}
