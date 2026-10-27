package es.udc.psi.cleanarchv2.app.domain.usecase;

import java.util.ArrayList;
import java.util.List;

import es.udc.psi.cleanarchv2.app.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.app.domain.model.Artist;
import es.udc.psi.cleanarchv2.app.domain.repository.ArtistRepository;

/**
 * Use case: search the artists whose name matches a text.
 */
public class SearchArtistsUseCase {

    private final ArtistRepository mRepository;

    public SearchArtistsUseCase(ArtistRepository repository) {

        mRepository = repository;
    }

    public List<Artist> execute(String textToSearch) throws DataUnavailableException {

        if (textToSearch == null || textToSearch.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return mRepository.searchArtists(textToSearch.trim());
    }
}
