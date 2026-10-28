package es.udc.psi.cleanarchv2.domain.repository;

import java.util.List;

import es.udc.psi.cleanarchv2.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.domain.model.Artist;

/**
 * Port of the domain: what the use cases need to know about artists.
 * The data layer implements it; the domain does not know where the artists come from.
 */
public interface ArtistRepository {

    List<Artist> searchArtists(String textToSearch) throws DataUnavailableException;
}
