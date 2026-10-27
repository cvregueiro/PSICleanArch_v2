package es.udc.psi.cleanarchv2.app.data.api;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Creates the Retrofit clients of the remote APIs.
 * The configuration (User-Agent, API key, logging) is received as parameters:
 * the data layer does not read the BuildConfig of the app.
 */
public final class ApiFactory {

    private static final String MUSICBRAINZ_URL = "https://musicbrainz.org/ws/2/";
    private static final String SETLISTFM_URL = "https://api.setlist.fm/rest/1.0/";

    private ApiFactory() {

    }

    public static MusicBrainzAPI createMusicBrainzApi(String userAgent,
                                                      boolean logging) {

        return createRetrofit(MUSICBRAINZ_URL, new MusicBrainzApiInterceptor(userAgent), logging)
                .create(MusicBrainzAPI.class);
    }

    public static SetListFmAPI createSetListFmApi(String apiKey,
                                                  boolean logging) {

        return createRetrofit(SETLISTFM_URL, new SetListFmApiInterceptor(apiKey), logging)
                .create(SetListFmAPI.class);
    }

    private static Retrofit createRetrofit(String baseUrl,
                                           Interceptor apiInterceptor,
                                           boolean logging) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(logging ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        // Never write the API key to the log
        loggingInterceptor.redactHeader(SetListFmApiInterceptor.API_KEY_HEADER);

        // The logging interceptor goes last to log the headers added by the API interceptor
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(apiInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();

        return new Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}
