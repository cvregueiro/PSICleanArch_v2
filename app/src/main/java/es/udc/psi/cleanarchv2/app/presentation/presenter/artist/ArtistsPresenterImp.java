package es.udc.psi.cleanarchv2.app.presentation.presenter.artist;

import android.util.Log;

import java.util.List;

import es.udc.psi.cleanarchv2.app.domain.model.Artist;
import es.udc.psi.cleanarchv2.app.domain.usecase.SearchArtistsUseCase;
import es.udc.psi.cleanarchv2.app.presentation.presenter.TaskRunner;
import es.udc.psi.cleanarchv2.app.presentation.uimodel.artist.ArtistUiModel;
import es.udc.psi.cleanarchv2.app.presentation.uimodel.artist.ArtistUiModelMapper;

public class ArtistsPresenterImp implements ArtistsPresenter {

    private final static String TAG = ArtistsPresenterImp.class.getSimpleName();

    private ArtistsView mView;

    private final SearchArtistsUseCase mSearchArtistsUseCase;
    private final TaskRunner mTaskRunner;

    public ArtistsPresenterImp(ArtistsView view,
                               SearchArtistsUseCase searchArtistsUseCase,
                               TaskRunner taskRunner) {

        mView = view;
        mSearchArtistsUseCase = searchArtistsUseCase;
        mTaskRunner = taskRunner;
    }

    @Override
    public void initFlow() {

        mView.showEmptyView();
    }

    @Override
    public void onSearch(String textToSearch) {

        String text = textToSearch.trim();
        if (text.isEmpty()) {

            mView.showEmptyQueryError();
            return;
        }

        mView.hideQueryError();
        mView.hideKeyboard();
        mView.showLoading();
        getArtists(text);
    }

    @Override
    public void onClickArtist(ArtistUiModel artist) {

        mView.navigateToGigs(artist.getId(), artist.getName());
    }

    @Override
    public void onDestroy() {

        mView = null;
    }

    private void getArtists(String textToSearch) {

        mTaskRunner.execute(() -> mSearchArtistsUseCase.execute(textToSearch), new TaskRunner.Callback<List<Artist>>() {

            @Override
            public void onSuccess(List<Artist> artists) {

                if (mView == null) {
                    return;
                }

                if (artists.isEmpty()) {
                    mView.showNoResults();
                } else {
                    mView.showArtists(getArtistsUiModel(artists));
                }
            }

            @Override
            public void onError(Exception exception) {

                Log.e(TAG, "Search of artists fails: " + exception.getMessage());
                if (mView != null) {
                    mView.showError();
                }
            }
        });
    }

    private List<ArtistUiModel> getArtistsUiModel(List<Artist> artists) {

        return new ArtistUiModelMapper(artists).map();
    }
}
