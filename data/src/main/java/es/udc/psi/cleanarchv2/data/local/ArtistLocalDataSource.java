package es.udc.psi.cleanarchv2.data.local;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.data.remote.dto.ArtistDto;

/**
 * Local copy of the searches of artists: it keeps the DTOs that the server returned
 * so that the same search can be answered without network.
 */
public interface ArtistLocalDataSource {

    List<ArtistDto> getArtists(String textToSearch) throws IOException;

    void saveArtists(String textToSearch,
                     List<ArtistDto> artists) throws IOException;
}
