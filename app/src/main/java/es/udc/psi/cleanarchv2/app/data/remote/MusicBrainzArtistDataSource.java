package es.udc.psi.cleanarchv2.app.data.remote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.udc.psi.cleanarchv2.app.data.remote.api.MusicBrainzAPI;
import es.udc.psi.cleanarchv2.app.data.remote.dto.ArtistDto;
import es.udc.psi.cleanarchv2.app.data.remote.dto.SearchArtistsDto;
import retrofit2.Response;

/**
 * Artists of MusicBrainz, obtained with Retrofit.
 */
public class MusicBrainzArtistDataSource implements ArtistRemoteDataSource {

    private static final String FORMAT = "json";

    private final MusicBrainzAPI mApi;

    public MusicBrainzArtistDataSource(MusicBrainzAPI api) {

        mApi = api;
    }

    @Override
    public List<ArtistDto> searchArtists(String textToSearch) throws IOException {

        Response<SearchArtistsDto> response = mApi.searchArtistByName(buildQuery(textToSearch), FORMAT).execute();

        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("MusicBrainz response fails: " + response.code());
        }

        List<ArtistDto> artists = response.body().getArtists();
        return artists != null ? artists : new ArrayList<>();
    }

    // Lucene query of MusicBrainz: the text is searched as a phrase in the artist field
    private String buildQuery(String textToSearch) {

        String phrase = textToSearch.replace("\\", "\\\\").replace("\"", "\\\"");
        return "artist:\"" + phrase + "\"";
    }
}
