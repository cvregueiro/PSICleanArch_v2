package es.udc.psi.cleanarchv2.app.di;

import android.content.Context;

import java.io.File;

import es.udc.psi.cleanarchv2.BuildConfig;
import es.udc.psi.cleanarchv2.app.data.local.FileArtistDataSource;
import es.udc.psi.cleanarchv2.app.data.local.FileGigDataSource;
import es.udc.psi.cleanarchv2.app.data.local.JsonFileStore;
import es.udc.psi.cleanarchv2.app.data.network.AndroidNetworkChecker;
import es.udc.psi.cleanarchv2.app.data.network.NetworkChecker;
import es.udc.psi.cleanarchv2.app.data.remote.MusicBrainzArtistDataSource;
import es.udc.psi.cleanarchv2.app.data.remote.SetListFmGigDataSource;
import es.udc.psi.cleanarchv2.app.data.remote.api.ApiFactory;
import es.udc.psi.cleanarchv2.app.data.repository.ArtistRepositoryImp;
import es.udc.psi.cleanarchv2.app.data.repository.GigRepositoryImp;
import es.udc.psi.cleanarchv2.app.presentation.presenter.TaskRunner;
import es.udc.psi.cleanarchv2.domain.repository.ArtistRepository;
import es.udc.psi.cleanarchv2.domain.repository.GigRepository;
import es.udc.psi.cleanarchv2.domain.usecase.GetArtistGigsUseCase;
import es.udc.psi.cleanarchv2.domain.usecase.SearchArtistsUseCase;

/**
 * Composition root: the only place of the app that knows the implementations
 * of the domain ports and joins the layers. GlobalApplication creates it once.
 */
public class AppContainer {

    // MusicBrainz rejects requests without a meaningful User-Agent
    private static final String USER_AGENT = "PSiCleanArch/" + BuildConfig.VERSION_NAME + " ( cvazquez@udc.es )";

    // Private directory of the app for the local copies (not the cache directory, that Android may empty)
    private static final String OFFLINE_DIRECTORY = "offline";

    private final ArtistRepository mArtistRepository;
    private final GigRepository mGigRepository;

    public AppContainer(Context context) {

        NetworkChecker networkChecker = new AndroidNetworkChecker(context);
        JsonFileStore store = new JsonFileStore(new File(context.getFilesDir(), OFFLINE_DIRECTORY));

        mArtistRepository = new ArtistRepositoryImp(
                new MusicBrainzArtistDataSource(ApiFactory.createMusicBrainzApi(USER_AGENT, BuildConfig.DEBUG)),
                new FileArtistDataSource(store),
                networkChecker);

        String apiKey = BuildConfig.SETLISTFM_API_KEY;
        mGigRepository = new GigRepositoryImp(
                new SetListFmGigDataSource(ApiFactory.createSetListFmApi(apiKey, BuildConfig.DEBUG), apiKey),
                new FileGigDataSource(store),
                networkChecker);
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
