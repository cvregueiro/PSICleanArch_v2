package es.udc.psi.cleanarchv2.app.presentation.artist.presenter;

import java.util.List;

import es.udc.psi.cleanarchv2.app.domain.Artist;

public interface ArtistsView {

    void showLoading();

    void showArtists(List<Artist> artists);

    void showEmptyView();

    void showNoResults();

    void showError();

    void showEmptyQueryError();

    void hideQueryError();

    void hideKeyboard();

    void navigateToGigs(String artistId, String artistName);
}
