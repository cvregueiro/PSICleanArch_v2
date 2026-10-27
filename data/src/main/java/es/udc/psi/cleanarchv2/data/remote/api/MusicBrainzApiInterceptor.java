package es.udc.psi.cleanarchv2.data.remote.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Adds the headers required by MusicBrainz to every request.
 * MusicBrainz rejects requests without a meaningful User-Agent:
 * https://musicbrainz.org/doc/MusicBrainz_API/Rate_Limiting
 */
public class MusicBrainzApiInterceptor implements Interceptor {

    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String ACCEPT_HEADER = "Accept";

    private static final String JSON_RESPONSE = "application/json";

    private final String mUserAgent;

    public MusicBrainzApiInterceptor(String userAgent) {

        mUserAgent = userAgent;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        Request request = chain.request().newBuilder()
                .header(USER_AGENT_HEADER, mUserAgent)
                .header(ACCEPT_HEADER, JSON_RESPONSE)
                .build();

        return chain.proceed(request);
    }
}
