package es.udc.psi.cleanarchv2.app.domain.gig.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import es.udc.psi.cleanarchv2.BuildConfig;
import es.udc.psi.cleanarchv2.app.data.SetListFmAPI;
import es.udc.psi.cleanarchv2.app.data.gig.dto.GigDtoMapper;
import es.udc.psi.cleanarchv2.app.data.gig.dto.SetListsDto;
import es.udc.psi.cleanarchv2.app.data.interceptor.SetListFmApiInterceptor;
import es.udc.psi.cleanarchv2.app.domain.exception.ConfigurationException;
import es.udc.psi.cleanarchv2.app.domain.gig.Gig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GigServiceImp implements GigService {

    @Override
    public List<Gig> searchGigs(String artistId) throws IOException {

        if (BuildConfig.SETLISTFM_API_KEY.isEmpty()) {
            throw new ConfigurationException("setlist.fm API key not found in local.properties");
        }

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        // Never write the API key to the log
        loggingInterceptor.redactHeader("x-api-key");
        List<Interceptor> interceptors = okHttpClient.interceptors();
        interceptors.add(new SetListFmApiInterceptor());
        interceptors.add(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.setlist.fm/rest/1.0/")
                .client(okHttpClient.build()).addConverterFactory(GsonConverterFactory.create()).build();

        SetListFmAPI api = retrofit.create(SetListFmAPI.class);

        Response<SetListsDto> response = api.searchSetLists(artistId).execute();

        // setlist.fm answers 404 when the artist has no setlists
        if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {
            return new ArrayList<>();
        }

        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("setlist.fm response fails: " + response.code());
        }

        return new GigDtoMapper(response.body().getSetLists()).map();
    }
}
