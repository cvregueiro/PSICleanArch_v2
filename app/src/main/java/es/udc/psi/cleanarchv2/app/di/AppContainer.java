package es.udc.psi.cleanarchv2.app.di;

import es.udc.psi.cleanarchv2.app.data.artist.ArtistDatasourceImp;
import es.udc.psi.cleanarchv2.app.domain.artist.service.ArtistService;
import es.udc.psi.cleanarchv2.app.domain.artist.service.ArtistServiceImp;
import es.udc.psi.cleanarchv2.app.domain.gig.service.GigService;
import es.udc.psi.cleanarchv2.app.domain.gig.service.GigServiceImp;
import es.udc.psi.cleanarchv2.app.executor.TaskRunner;

/**
 * Container of the dependencies of the app: the only place that knows the implementations.
 * GlobalApplication creates it once and it lives as long as the process.
 */
public class AppContainer {

    private final ArtistService mArtistService;
    private final GigService mGigService;

    public AppContainer() {

        mArtistService = new ArtistServiceImp(new ArtistDatasourceImp());
        mGigService = new GigServiceImp();
    }

    public ArtistService artistService() {

        return mArtistService;
    }

    public GigService gigService() {

        return mGigService;
    }

    public TaskRunner taskRunner() {

        return new TaskRunner();
    }
}
