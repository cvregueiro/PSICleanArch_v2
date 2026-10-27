package es.udc.psi.cleanarchv2.app.presentation.presenter.artist;

import java.util.List;

import es.udc.psi.cleanarchv2.app.presentation.uimodel.artist.ArtistUiModel;

public interface ArtistsView {

    void showLoading();

    void showArtists(List<ArtistUiModel> artists);

    void showEmptyView();

    void showNoResults();

    void showError();

    void showEmptyQueryError();

    void hideQueryError();

    void hideKeyboard();

    void navigateToGigs(String artistId, String artistName);
}
