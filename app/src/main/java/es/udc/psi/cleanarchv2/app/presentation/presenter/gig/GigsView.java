package es.udc.psi.cleanarchv2.app.presentation.presenter.gig;

import java.util.List;

import es.udc.psi.cleanarchv2.app.presentation.uimodel.gig.GigUiModel;

public interface GigsView {

    void showLoading();

    void showGigs(List<GigUiModel> gigs);

    void showEmptyView();

    void showMissingApiKey();

    void showError();
}
