package es.udc.psi.cleanarchv2.app;

import android.app.Application;

import es.udc.psi.cleanarchv2.app.di.AppContainer;

/**
 * Android creates it once, before any activity. It creates the container of dependencies,
 * the composition root of the Clean Architecture: no inner layer knows it.
 */
public class GlobalApplication extends Application {

    private AppContainer mContainer;

    @Override
    public void onCreate() {

        super.onCreate();
        mContainer = new AppContainer(this);
    }

    public AppContainer getContainer() {

        return mContainer;
    }
}
