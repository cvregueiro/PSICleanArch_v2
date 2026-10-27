package es.udc.psi.cleanarchv2.data;

import java.io.File;

import es.udc.psi.cleanarchv2.data.local.FileArtistDataSource;
import es.udc.psi.cleanarchv2.data.local.FileGigDataSource;
import es.udc.psi.cleanarchv2.data.local.JsonFileStore;
import es.udc.psi.cleanarchv2.data.network.NetworkChecker;
import es.udc.psi.cleanarchv2.data.remote.MusicBrainzArtistDataSource;
import es.udc.psi.cleanarchv2.data.remote.SetListFmGigDataSource;
import es.udc.psi.cleanarchv2.data.remote.api.ApiFactory;
import es.udc.psi.cleanarchv2.data.repository.ArtistRepositoryImp;
import es.udc.psi.cleanarchv2.data.repository.GigRepositoryImp;
import es.udc.psi.cleanarchv2.domain.repository.ArtistRepository;
import es.udc.psi.cleanarchv2.domain.repository.GigRepository;

/**
 * Public entry point of the data layer: builds the repositories with their remote and local sources.
 * Outer modules only see the domain ports (ArtistRepository, GigRepository) and NetworkChecker,
 * never Retrofit, OkHttp, Gson or the DTOs.
 */
public final class RepositoryFactory {

    private RepositoryFactory() {

    }

    public static ArtistRepository createArtistRepository(String userAgent,
                                                          boolean logging,
                                                          File offlineDirectory,
                                                          NetworkChecker networkChecker) {

        return new ArtistRepositoryImp(
                new MusicBrainzArtistDataSource(ApiFactory.createMusicBrainzApi(userAgent, logging)),
                new FileArtistDataSource(new JsonFileStore(offlineDirectory)),
                networkChecker);
    }

    public static GigRepository createGigRepository(String apiKey,
                                                    boolean logging,
                                                    File offlineDirectory,
                                                    NetworkChecker networkChecker) {

        return new GigRepositoryImp(
                new SetListFmGigDataSource(ApiFactory.createSetListFmApi(apiKey, logging), apiKey),
                new FileGigDataSource(new JsonFileStore(offlineDirectory)),
                networkChecker);
    }
}
