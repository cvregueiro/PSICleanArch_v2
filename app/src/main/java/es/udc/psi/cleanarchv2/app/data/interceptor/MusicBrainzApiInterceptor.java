package es.udc.psi.cleanarchv2.app.data.interceptor;

import java.io.IOException;

import androidx.annotation.NonNull;
import es.udc.psi.cleanarchv2.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MusicBrainzApiInterceptor implements Interceptor {

    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String ACCEPT_HEADER = "Accept";

    // MusicBrainz rejects requests without a meaningful User-Agent:
    // https://musicbrainz.org/doc/MusicBrainz_API/Rate_Limiting
    private static final String USER_AGENT = "PSiCleanArch/" + BuildConfig.VERSION_NAME + " ( cvazquez@udc.es )";
    private static final String JSON_RESPONSE = "application/json";

    @NonNull
    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {

        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder();

        requestBuilder.header(USER_AGENT_HEADER, USER_AGENT);
        requestBuilder.header(ACCEPT_HEADER, JSON_RESPONSE);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
