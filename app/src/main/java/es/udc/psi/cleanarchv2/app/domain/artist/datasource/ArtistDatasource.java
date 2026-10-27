package es.udc.psi.cleanarchv2.app.domain.artist.datasource;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.app.domain.model.Artist;

public interface ArtistDatasource {

    List<Artist> searchArtists(String textToSearch) throws IOException;
}
