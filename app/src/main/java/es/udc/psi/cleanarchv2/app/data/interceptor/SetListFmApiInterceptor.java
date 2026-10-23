package es.udc.psi.cleanarchv2.app.data.interceptor;

import java.io.IOException;

import androidx.annotation.NonNull;
import es.udc.psi.cleanarchv2.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SetListFmApiInterceptor implements Interceptor {

    private static final String API_KEY_HEADER = "x-api-key";
    private static final String ACCEPT_HEADER = "Accept";

    // Read from local.properties (setlistfm.apikey), never committed to the repository
    private static final String API_KEY = BuildConfig.SETLISTFM_API_KEY;
    private static final String JSON_RESPONSE = "application/json";

    @NonNull
    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {

        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder();

        requestBuilder.header(API_KEY_HEADER, API_KEY);
        requestBuilder.header(ACCEPT_HEADER, JSON_RESPONSE);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
