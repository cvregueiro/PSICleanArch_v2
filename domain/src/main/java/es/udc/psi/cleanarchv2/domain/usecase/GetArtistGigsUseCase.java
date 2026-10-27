package es.udc.psi.cleanarchv2.domain.usecase;

import java.util.ArrayList;
import java.util.List;

import es.udc.psi.cleanarchv2.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.domain.model.Gig;
import es.udc.psi.cleanarchv2.domain.repository.GigRepository;

/**
 * Use case: get the gigs of an artist.
 */
public class GetArtistGigsUseCase {

    private final GigRepository mRepository;

    public GetArtistGigsUseCase(GigRepository repository) {

        mRepository = repository;
    }

    public List<Gig> execute(String artistId) throws DataUnavailableException {

        if (artistId == null || artistId.isEmpty()) {
            return new ArrayList<>();
        }

        return mRepository.getGigs(artistId);
    }
}
