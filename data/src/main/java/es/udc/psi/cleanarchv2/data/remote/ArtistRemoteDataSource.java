package es.udc.psi.cleanarchv2.data.remote;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.data.remote.dto.ArtistDto;

/**
 * Remote source of artists. It works with DTOs and IOException: details of the data layer
 * that the repository translates to the domain.
 */
public interface ArtistRemoteDataSource {

    List<ArtistDto> searchArtists(String textToSearch) throws IOException;
}
