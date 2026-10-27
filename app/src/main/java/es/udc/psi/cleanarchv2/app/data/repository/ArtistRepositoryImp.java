package es.udc.psi.cleanarchv2.app.data.repository;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.app.data.api.MusicBrainzAPI;
import es.udc.psi.cleanarchv2.app.data.api.dto.ArtistDtoMapper;
import es.udc.psi.cleanarchv2.app.data.api.dto.SearchArtistsDto;
import es.udc.psi.cleanarchv2.app.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.app.domain.model.Artist;
import es.udc.psi.cleanarchv2.app.domain.repository.ArtistRepository;
import retrofit2.Response;

/**
 * Adapter of the ArtistRepository port: searches the artists in MusicBrainz,
 * maps the DTOs to entities and translates the I/O errors to domain exceptions.
 */
public class ArtistRepositoryImp implements ArtistRepository {

    private static final String FORMAT = "json";

    private final MusicBrainzAPI mApi;

    public ArtistRepositoryImp(MusicBrainzAPI api) {

        mApi = api;
    }

    @Override
    public List<Artist> searchArtists(String textToSearch) throws DataUnavailableException {

        try {
            Response<SearchArtistsDto> response = mApi.searchArtistByName(buildQuery(textToSearch), FORMAT).execute();

            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("MusicBrainz response fails: " + response.code());
            }

            return new ArtistDtoMapper(response.body().getArtists()).map();
        } catch (IOException e) {
            throw new DataUnavailableException("Artists not available", e);
        }
    }

    // Lucene query of MusicBrainz: the text is searched as a phrase in the artist field
    private String buildQuery(String textToSearch) {

        String phrase = textToSearch.replace("\\", "\\\\").replace("\"", "\\\"");
        return "artist:\"" + phrase + "\"";
    }
}
