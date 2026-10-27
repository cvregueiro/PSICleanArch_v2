package es.udc.psi.cleanarchv2.app.presentation.presenter.artist;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.udc.psi.cleanarchv2.app.presentation.presenter.TaskRunner;
import es.udc.psi.cleanarchv2.app.presentation.uimodel.artist.ArtistUiModel;
import es.udc.psi.cleanarchv2.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.domain.model.Artist;
import es.udc.psi.cleanarchv2.domain.repository.ArtistRepository;
import es.udc.psi.cleanarchv2.domain.usecase.SearchArtistsUseCase;

import static org.junit.Assert.assertEquals;

public class ArtistsPresenterImpTest {

    // Runs the tasks in the calling thread
    private final TaskRunner mTaskRunner = new TaskRunner(Runnable::run, Runnable::run);

    private final FakeArtistsView mView = new FakeArtistsView();

    private ArtistsPresenter createPresenter(ArtistRepository repository) {

        return new ArtistsPresenterImp(mView, new SearchArtistsUseCase(repository), mTaskRunner);
    }

    @Test
    public void onSearch_withEmptyText_showsQueryError() {

        createPresenter(text -> new ArrayList<>()).onSearch("  ");

        assertEquals(Collections.singletonList("showEmptyQueryError"), mView.calls);
    }

    @Test
    public void onSearch_showsTheArtistsFound() {

        createPresenter(text -> Collections.singletonList(new Artist("id", "Rancid"))).onSearch("rancid");

        assertEquals("showArtists", mView.lastCall());
        assertEquals("Rancid", mView.artists.get(0).getName());
    }

    @Test
    public void onSearch_withoutResults_showsNoResults() {

        createPresenter(text -> new ArrayList<>()).onSearch("zzzz");

        assertEquals("showNoResults", mView.lastCall());
    }

    @Test
    public void onSearch_whenTheDataIsNotAvailable_showsError() {

        createPresenter(text -> {
            throw new DataUnavailableException("network down");
        }).onSearch("rancid");

        assertEquals("showError", mView.lastCall());
    }

    @Test
    public void onClickArtist_navigatesToItsGigs() {

        createPresenter(text -> new ArrayList<>()).onClickArtist(new ArtistUiModel("id", "Rancid"));

        assertEquals("navigateToGigs id Rancid", mView.lastCall());
    }

    private static class FakeArtistsView implements ArtistsView {

        private final List<String> calls = new ArrayList<>();
        private List<ArtistUiModel> artists;

        private String lastCall() {

            return calls.get(calls.size() - 1);
        }

        @Override
        public void showLoading() {

            calls.add("showLoading");
        }

        @Override
        public void showArtists(List<ArtistUiModel> artists) {

            this.artists = artists;
            calls.add("showArtists");
        }

        @Override
        public void showEmptyView() {

            calls.add("showEmptyView");
        }

        @Override
        public void showNoResults() {

            calls.add("showNoResults");
        }

        @Override
        public void showError() {

            calls.add("showError");
        }

        @Override
        public void showEmptyQueryError() {

            calls.add("showEmptyQueryError");
        }

        @Override
        public void hideQueryError() {

            calls.add("hideQueryError");
        }

        @Override
        public void hideKeyboard() {

            calls.add("hideKeyboard");
        }

        @Override
        public void navigateToGigs(String artistId,
                                   String artistName) {

            calls.add("navigateToGigs " + artistId + " " + artistName);
        }
    }
}
