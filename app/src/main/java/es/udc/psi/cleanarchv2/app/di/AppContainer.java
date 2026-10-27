package es.udc.psi.cleanarchv2.app.di;

import android.content.Context;

import java.io.File;

import es.udc.psi.cleanarchv2.BuildConfig;
import es.udc.psi.cleanarchv2.app.network.AndroidNetworkChecker;
import es.udc.psi.cleanarchv2.app.presentation.presenter.TaskRunner;
import es.udc.psi.cleanarchv2.data.RepositoryFactory;
import es.udc.psi.cleanarchv2.data.network.NetworkChecker;
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
        File offlineDirectory = new File(context.getFilesDir(), OFFLINE_DIRECTORY);

        mArtistRepository = RepositoryFactory.createArtistRepository(
                USER_AGENT, BuildConfig.DEBUG, offlineDirectory, networkChecker);
        mGigRepository = RepositoryFactory.createGigRepository(
                BuildConfig.SETLISTFM_API_KEY, BuildConfig.DEBUG, offlineDirectory, networkChecker);
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
