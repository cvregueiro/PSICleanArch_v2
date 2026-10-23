package es.udc.psi.cleanarchv2.app.domain.artist.service;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.app.domain.artist.Artist;

public interface ArtistService {

    List<Artist> searchArtists(String textToSearch) throws IOException;
}
