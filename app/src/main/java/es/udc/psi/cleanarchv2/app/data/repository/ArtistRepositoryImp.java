package es.udc.psi.cleanarchv2.app.data.repository;

import java.io.IOException;
import java.util.List;

import es.udc.psi.cleanarchv2.app.data.remote.ArtistRemoteDataSource;
import es.udc.psi.cleanarchv2.app.data.remote.dto.ArtistDtoMapper;
import es.udc.psi.cleanarchv2.app.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.app.domain.model.Artist;
import es.udc.psi.cleanarchv2.app.domain.repository.ArtistRepository;

/**
 * Adapter of the ArtistRepository port: gets the artists from the remote source,
 * maps the DTOs to entities and translates the I/O errors to domain exceptions.
 */
public class ArtistRepositoryImp implements ArtistRepository {

    private final ArtistRemoteDataSource mRemoteDataSource;

    public ArtistRepositoryImp(ArtistRemoteDataSource remoteDataSource) {

        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public List<Artist> searchArtists(String textToSearch) throws DataUnavailableException {

        try {
            return new ArtistDtoMapper(mRemoteDataSource.searchArtists(textToSearch)).map();
        } catch (IOException e) {
            throw new DataUnavailableException("Artists not available", e);
        }
    }
}
