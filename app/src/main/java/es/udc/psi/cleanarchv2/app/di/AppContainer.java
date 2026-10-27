package es.udc.psi.cleanarchv2.app.di;

import es.udc.psi.cleanarchv2.BuildConfig;
import es.udc.psi.cleanarchv2.app.data.api.ApiFactory;
import es.udc.psi.cleanarchv2.app.data.repository.ArtistRepositoryImp;
import es.udc.psi.cleanarchv2.app.domain.gig.service.GigService;
import es.udc.psi.cleanarchv2.app.domain.gig.service.GigServiceImp;
import es.udc.psi.cleanarchv2.app.domain.repository.ArtistRepository;
import es.udc.psi.cleanarchv2.app.domain.usecase.SearchArtistsUseCase;
import es.udc.psi.cleanarchv2.app.executor.TaskRunner;

/**
 * Container of the dependencies of the app: the only place that knows the implementations.
 * GlobalApplication creates it once and it lives as long as the process.
 */
public class AppContainer {

    // MusicBrainz rejects requests without a meaningful User-Agent
    private static final String USER_AGENT = "PSiCleanArch/" + BuildConfig.VERSION_NAME + " ( cvazquez@udc.es )";

    private final ArtistRepository mArtistRepository;
    private final GigService mGigService;

    public AppContainer() {

        mArtistRepository = new ArtistRepositoryImp(ApiFactory.createMusicBrainzApi(USER_AGENT, BuildConfig.DEBUG));

        String apiKey = BuildConfig.SETLISTFM_API_KEY;
        mGigService = new GigServiceImp(ApiFactory.createSetListFmApi(apiKey, BuildConfig.DEBUG), apiKey);
    }

    public SearchArtistsUseCase searchArtistsUseCase() {

        return new SearchArtistsUseCase(mArtistRepository);
    }

    public GigService gigService() {

        return mGigService;
    }

    public TaskRunner taskRunner() {

        return new TaskRunner();
    }
}
