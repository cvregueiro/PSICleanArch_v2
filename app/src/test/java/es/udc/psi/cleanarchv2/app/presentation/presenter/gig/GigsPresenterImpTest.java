package es.udc.psi.cleanarchv2.app.presentation.presenter.gig;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.udc.psi.cleanarchv2.app.presentation.presenter.TaskRunner;
import es.udc.psi.cleanarchv2.app.presentation.uimodel.gig.GigUiModel;
import es.udc.psi.cleanarchv2.domain.exception.ConfigurationException;
import es.udc.psi.cleanarchv2.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.domain.model.Gig;
import es.udc.psi.cleanarchv2.domain.repository.GigRepository;
import es.udc.psi.cleanarchv2.domain.usecase.GetArtistGigsUseCase;

import static org.junit.Assert.assertEquals;

public class GigsPresenterImpTest {

    // Runs the tasks in the calling thread
    private final TaskRunner mTaskRunner = new TaskRunner(Runnable::run, Runnable::run);

    private final FakeGigsView mView = new FakeGigsView();

    private void initFlow(GigRepository repository) {

        new GigsPresenterImp(mView, new GetArtistGigsUseCase(repository), mTaskRunner).initFlow("artist");
    }

    @Test
    public void initFlow_showsTheGigs() {

        initFlow(artistId -> Collections.singletonList(
                new Gig("1", null, "Petco Park", "San Diego", "United States", null)));

        assertEquals("showGigs", mView.lastCall());
        assertEquals("Petco Park", mView.gigs.get(0).getVenue());
    }

    @Test
    public void initFlow_withoutGigs_showsEmptyView() {

        initFlow(artistId -> new ArrayList<>());

        assertEquals("showEmptyView", mView.lastCall());
    }

    @Test
    public void initFlow_withoutApiKey_showsMissingApiKey() {

        initFlow(artistId -> {
            throw new ConfigurationException("no API key");
        });

        assertEquals("showMissingApiKey", mView.lastCall());
    }

    @Test
    public void initFlow_whenTheDataIsNotAvailable_showsError() {

        initFlow(artistId -> {
            throw new DataUnavailableException("network down");
        });

        assertEquals("showError", mView.lastCall());
    }

    private static class FakeGigsView implements GigsView {

        private final List<String> calls = new ArrayList<>();
        private List<GigUiModel> gigs;

        private String lastCall() {

            return calls.get(calls.size() - 1);
        }

        @Override
        public void showLoading() {

            calls.add("showLoading");
        }

        @Override
        public void showGigs(List<GigUiModel> gigs) {

            this.gigs = gigs;
            calls.add("showGigs");
        }

        @Override
        public void showEmptyView() {

            calls.add("showEmptyView");
        }

        @Override
        public void showMissingApiKey() {

            calls.add("showMissingApiKey");
        }

        @Override
        public void showError() {

            calls.add("showError");
        }
    }
}
