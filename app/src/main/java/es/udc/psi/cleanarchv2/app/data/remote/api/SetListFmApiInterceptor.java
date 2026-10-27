package es.udc.psi.cleanarchv2.app.data.remote.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Adds the API key and the JSON format required by setlist.fm to every request.
 */
public class SetListFmApiInterceptor implements Interceptor {

    public static final String API_KEY_HEADER = "x-api-key";
    private static final String ACCEPT_HEADER = "Accept";

    private static final String JSON_RESPONSE = "application/json";

    private final String mApiKey;

    public SetListFmApiInterceptor(String apiKey) {

        mApiKey = apiKey;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        Request request = chain.request().newBuilder()
                .header(API_KEY_HEADER, mApiKey)
                .header(ACCEPT_HEADER, JSON_RESPONSE)
                .build();

        return chain.proceed(request);
    }
}
