package es.udc.psi.cleanarchv2.app.presentation.gig.presenter;

import android.util.Log;

import java.util.List;

import es.udc.psi.cleanarchv2.app.domain.exception.ConfigurationException;
import es.udc.psi.cleanarchv2.app.domain.model.Gig;
import es.udc.psi.cleanarchv2.app.domain.usecase.GetArtistGigsUseCase;
import es.udc.psi.cleanarchv2.app.executor.TaskRunner;
import es.udc.psi.cleanarchv2.app.presentation.gig.model.GigUiModel;
import es.udc.psi.cleanarchv2.app.presentation.gig.model.GigUiModelMapper;

public class GigsPresenterImp implements GigsPresenter {

    private final static String TAG = GigsPresenterImp.class.getSimpleName();

    private GigsView mView;

    private final GetArtistGigsUseCase mGetArtistGigsUseCase;
    private final TaskRunner mTaskRunner;

    public GigsPresenterImp(GigsView view,
                            GetArtistGigsUseCase getArtistGigsUseCase,
                            TaskRunner taskRunner) {

        mView = view;
        mGetArtistGigsUseCase = getArtistGigsUseCase;
        mTaskRunner = taskRunner;
    }

    @Override
    public void initFlow(String artistId) {

        mView.showLoading();
        getGigs(artistId);
    }

    @Override
    public void onDestroy() {

        mView = null;
    }

    private void getGigs(String artistId) {

        mTaskRunner.execute(() -> mGetArtistGigsUseCase.execute(artistId), new TaskRunner.Callback<List<Gig>>() {

            @Override
            public void onSuccess(List<Gig> gigs) {

                if (mView == null) {
                    return;
                }

                if (gigs.isEmpty()) {
                    mView.showEmptyView();
                } else {
                    mView.showGigs(getGigsUiModel(gigs));
                }
            }

            @Override
            public void onError(Exception exception) {

                Log.e(TAG, "Search of gigs fails: " + exception.getMessage());
                if (mView == null) {
                    return;
                }

                if (exception instanceof ConfigurationException) {
                    mView.showMissingApiKey();
                } else {
                    mView.showError();
                }
            }
        });
    }

    private List<GigUiModel> getGigsUiModel(List<Gig> gigs) {

        return new GigUiModelMapper(gigs).map();
    }
}
