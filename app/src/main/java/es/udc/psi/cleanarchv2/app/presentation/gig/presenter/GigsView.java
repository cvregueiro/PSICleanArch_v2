package es.udc.psi.cleanarchv2.app.presentation.gig.presenter;

import java.util.List;

import es.udc.psi.cleanarchv2.app.domain.SetList;

public interface GigsView {

    void showLoading();

    void showGigs(List<SetList> gigs);

    void showEmptyView();

    void showMissingApiKey();

    void showError();
}
