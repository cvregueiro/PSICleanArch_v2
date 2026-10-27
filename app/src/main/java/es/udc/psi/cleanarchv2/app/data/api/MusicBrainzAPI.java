package es.udc.psi.cleanarchv2.app.data.api;

import es.udc.psi.cleanarchv2.app.data.api.dto.SearchArtistsDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MusicBrainzAPI {

    // https://musicbrainz.org/ws/2/artist/?query=artist:rancid&fmt=json
    @GET("artist/")
    Call<SearchArtistsDto> searchArtistByName(@Query("query") String query,
                                              @Query("fmt") String format);
}
