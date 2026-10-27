package es.udc.psi.cleanarchv2.app.data.artist;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.BuildConfig;
import es.udc.psi.cleanarchv2.app.data.MusicBrainzAPI;
import es.udc.psi.cleanarchv2.app.data.artist.dto.ArtistDtoMapper;
import es.udc.psi.cleanarchv2.app.data.artist.dto.SearchArtistsDto;
import es.udc.psi.cleanarchv2.app.data.interceptor.MusicBrainzApiInterceptor;
import es.udc.psi.cleanarchv2.app.domain.model.Artist;
import es.udc.psi.cleanarchv2.app.domain.artist.datasource.ArtistDatasource;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArtistDatasourceImp implements ArtistDatasource {

    @Override
    public List<Artist> searchArtists(String textToSearch) throws IOException {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        List<Interceptor> interceptors = okHttpClient.interceptors();
        interceptors.add(loggingInterceptor);
        interceptors.add(new MusicBrainzApiInterceptor());

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://musicbrainz.org/ws/2/").client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create()).build();

        MusicBrainzAPI api = retrofit.create(MusicBrainzAPI.class);

        String query = buildQuery(textToSearch);
        String format = "json";
        Response<SearchArtistsDto> response = api.searchArtistByName(query, format).execute();

        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("MusicBrainz response fails: " + response.code());
        }

        return new ArtistDtoMapper(response.body().getArtists()).map();
    }

    // Lucene query of MusicBrainz: the text is searched as a phrase in the artist field
    private String buildQuery(String textToSearch) {

        String phrase = textToSearch.replace("\\", "\\\\").replace("\"", "\\\"");
        return "artist:\"" + phrase + "\"";
    }
}
