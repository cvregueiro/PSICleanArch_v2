package es.udc.psi.cleanarchv2.app.data.repository;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.app.data.local.GigLocalDataSource;
import es.udc.psi.cleanarchv2.app.data.network.NetworkChecker;
import es.udc.psi.cleanarchv2.app.data.remote.GigRemoteDataSource;
import es.udc.psi.cleanarchv2.app.data.remote.MissingApiKeyException;
import es.udc.psi.cleanarchv2.app.data.remote.dto.GigDtoMapper;
import es.udc.psi.cleanarchv2.app.data.remote.dto.SetListDto;
import es.udc.psi.cleanarchv2.domain.exception.ConfigurationException;
import es.udc.psi.cleanarchv2.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.domain.model.Gig;
import es.udc.psi.cleanarchv2.domain.repository.GigRepository;

/**
 * Adapter of the GigRepository port. With network it gets the setlists from setlist.fm
 * and saves a local copy; without network it reads the local copy.
 * It maps the setlists to gigs and translates the errors to domain exceptions.
 */
public class GigRepositoryImp implements GigRepository {

    private final GigRemoteDataSource mRemoteDataSource;
    private final GigLocalDataSource mLocalDataSource;
    private final NetworkChecker mNetworkChecker;

    public GigRepositoryImp(GigRemoteDataSource remoteDataSource,
                            GigLocalDataSource localDataSource,
                            NetworkChecker networkChecker) {

        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
        mNetworkChecker = networkChecker;
    }

    @Override
    public List<Gig> getGigs(String artistId) throws DataUnavailableException {

        try {
            return new GigDtoMapper(getSetLists(artistId)).map();
        } catch (MissingApiKeyException e) {
            throw new ConfigurationException(e.getMessage());
        } catch (IOException e) {
            throw new DataUnavailableException("Gigs not available", e);
        }
    }

    private List<SetListDto> getSetLists(String artistId) throws IOException {

        if (!mNetworkChecker.isNetworkAvailable()) {
            return mLocalDataSource.getSetLists(artistId);
        }

        List<SetListDto> setLists = mRemoteDataSource.searchSetLists(artistId);
        try {
            mLocalDataSource.saveSetLists(artistId, setLists);
        } catch (IOException e) {
            // The gigs have been obtained: without the copy they just will not be available offline
        }
        return setLists;
    }
}
