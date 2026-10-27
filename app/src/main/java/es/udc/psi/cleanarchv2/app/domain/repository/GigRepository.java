package es.udc.psi.cleanarchv2.app.domain.repository;

import java.util.List;

import es.udc.psi.cleanarchv2.app.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.app.domain.model.Gig;

/**
 * Port of the domain: what the use cases need to know about the gigs of an artist.
 */
public interface GigRepository {

    List<Gig> getGigs(String artistId) throws DataUnavailableException;
}
