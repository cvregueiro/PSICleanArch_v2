package es.udc.psi.cleanarchv2.app.data.artist;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.app.data.api.MusicBrainzAPI;
import es.udc.psi.cleanarchv2.app.data.api.dto.ArtistDtoMapper;
import es.udc.psi.cleanarchv2.app.data.api.dto.SearchArtistsDto;
import es.udc.psi.cleanarchv2.app.domain.artist.datasource.ArtistDatasource;
import es.udc.psi.cleanarchv2.app.domain.model.Artist;
import retrofit2.Response;

public class ArtistDatasourceImp implements ArtistDatasource {

    private static final String FORMAT = "json";

    private final MusicBrainzAPI mApi;

    public ArtistDatasourceImp(MusicBrainzAPI api) {

        mApi = api;
    }

    @Override
    public List<Artist> searchArtists(String textToSearch) throws IOException {

        Response<SearchArtistsDto> response = mApi.searchArtistByName(buildQuery(textToSearch), FORMAT).execute();

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
