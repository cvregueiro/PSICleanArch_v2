package es.udc.psi.cleanarchv2.app.data;

import es.udc.psi.cleanarchv2.BuildConfig;
import es.udc.psi.cleanarchv2.app.domain.SearchArtists;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MusicBrainzAPI {

    // https://musicbrainz.org/ws/2/artist/?query=artist:rancid&fmt=json
    // MusicBrainz rejects requests without a meaningful User-Agent
    @Headers("User-Agent: PSiCleanArch/" + BuildConfig.VERSION_NAME + " ( cvazquez@udc.es )")
    @GET("artist/")
    Call<SearchArtists> searchArtistByName(@Query("query") String query,
                                           @Query("fmt") String format);
}
