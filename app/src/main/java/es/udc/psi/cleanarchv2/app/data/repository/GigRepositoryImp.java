package es.udc.psi.cleanarchv2.app.data.repository;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.app.data.remote.GigRemoteDataSource;
import es.udc.psi.cleanarchv2.app.data.remote.MissingApiKeyException;
import es.udc.psi.cleanarchv2.app.data.remote.dto.GigDtoMapper;
import es.udc.psi.cleanarchv2.app.domain.exception.ConfigurationException;
import es.udc.psi.cleanarchv2.app.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.app.domain.model.Gig;
import es.udc.psi.cleanarchv2.app.domain.repository.GigRepository;

/**
 * Adapter of the GigRepository port: gets the setlists from the remote source,
 * maps them to gigs and translates the errors to domain exceptions.
 */
public class GigRepositoryImp implements GigRepository {

    private final GigRemoteDataSource mRemoteDataSource;

    public GigRepositoryImp(GigRemoteDataSource remoteDataSource) {

        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public List<Gig> getGigs(String artistId) throws DataUnavailableException {

        try {
            return new GigDtoMapper(mRemoteDataSource.searchSetLists(artistId)).map();
        } catch (MissingApiKeyException e) {
            throw new ConfigurationException(e.getMessage());
        } catch (IOException e) {
            throw new DataUnavailableException("Gigs not available", e);
        }
    }
}
