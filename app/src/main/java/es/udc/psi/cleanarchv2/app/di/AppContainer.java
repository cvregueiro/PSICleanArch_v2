package es.udc.psi.cleanarchv2.app.di;

import es.udc.psi.cleanarchv2.BuildConfig;
import es.udc.psi.cleanarchv2.app.data.remote.MusicBrainzArtistDataSource;
import es.udc.psi.cleanarchv2.app.data.remote.SetListFmGigDataSource;
import es.udc.psi.cleanarchv2.app.data.remote.api.ApiFactory;
import es.udc.psi.cleanarchv2.app.data.repository.ArtistRepositoryImp;
import es.udc.psi.cleanarchv2.app.data.repository.GigRepositoryImp;
import es.udc.psi.cleanarchv2.app.domain.repository.ArtistRepository;
import es.udc.psi.cleanarchv2.app.domain.repository.GigRepository;
import es.udc.psi.cleanarchv2.app.domain.usecase.GetArtistGigsUseCase;
import es.udc.psi.cleanarchv2.app.domain.usecase.SearchArtistsUseCase;
import es.udc.psi.cleanarchv2.app.presentation.presenter.TaskRunner;

/**
 * Composition root: the only place of the app that knows the implementations
 * of the domain ports and joins the layers. GlobalApplication creates it once.
 */
public class AppContainer {

    // MusicBrainz rejects requests without a meaningful User-Agent
    private static final String USER_AGENT = "PSiCleanArch/" + BuildConfig.VERSION_NAME + " ( cvazquez@udc.es )";

    private final ArtistRepository mArtistRepository;
    private final GigRepository mGigRepository;

    public AppContainer() {

        mArtistRepository = new ArtistRepositoryImp(
                new MusicBrainzArtistDataSource(ApiFactory.createMusicBrainzApi(USER_AGENT, BuildConfig.DEBUG)));

        String apiKey = BuildConfig.SETLISTFM_API_KEY;
        mGigRepository = new GigRepositoryImp(
                new SetListFmGigDataSource(ApiFactory.createSetListFmApi(apiKey, BuildConfig.DEBUG), apiKey));
    }

    public SearchArtistsUseCase searchArtistsUseCase() {

        return new SearchArtistsUseCase(mArtistRepository);
    }

    public GetArtistGigsUseCase getArtistGigsUseCase() {

        return new GetArtistGigsUseCase(mGigRepository);
    }

    public TaskRunner taskRunner() {

        return new TaskRunner();
    }
}
