package es.udc.psi.cleanarchv2.app.data.repository;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.app.data.local.ArtistLocalDataSource;
import es.udc.psi.cleanarchv2.app.data.network.NetworkChecker;
import es.udc.psi.cleanarchv2.app.data.remote.ArtistRemoteDataSource;
import es.udc.psi.cleanarchv2.app.data.remote.dto.ArtistDto;
import es.udc.psi.cleanarchv2.app.data.remote.dto.ArtistDtoMapper;
import es.udc.psi.cleanarchv2.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.domain.model.Artist;
import es.udc.psi.cleanarchv2.domain.repository.ArtistRepository;

/**
 * Adapter of the ArtistRepository port. It decides where the artists come from:
 * with network, from the server (and it saves a local copy); without network, from the local copy.
 * It maps the DTOs to entities and translates the I/O errors to domain exceptions.
 */
public class ArtistRepositoryImp implements ArtistRepository {

    private final ArtistRemoteDataSource mRemoteDataSource;
    private final ArtistLocalDataSource mLocalDataSource;
    private final NetworkChecker mNetworkChecker;

    public ArtistRepositoryImp(ArtistRemoteDataSource remoteDataSource,
                               ArtistLocalDataSource localDataSource,
                               NetworkChecker networkChecker) {

        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
        mNetworkChecker = networkChecker;
    }

    @Override
    public List<Artist> searchArtists(String textToSearch) throws DataUnavailableException {

        try {
            return new ArtistDtoMapper(getArtists(textToSearch)).map();
        } catch (IOException e) {
            throw new DataUnavailableException("Artists not available", e);
        }
    }

    private List<ArtistDto> getArtists(String textToSearch) throws IOException {

        if (!mNetworkChecker.isNetworkAvailable()) {
            return mLocalDataSource.getArtists(textToSearch);
        }

        List<ArtistDto> artists = mRemoteDataSource.searchArtists(textToSearch);
        try {
            mLocalDataSource.saveArtists(textToSearch, artists);
        } catch (IOException e) {
            // The search has worked: without the copy it just will not be available offline
        }
        return artists;
    }
}
