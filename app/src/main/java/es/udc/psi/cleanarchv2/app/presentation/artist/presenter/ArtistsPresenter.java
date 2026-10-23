package es.udc.psi.cleanarchv2.app.presentation.artist.presenter;

import es.udc.psi.cleanarchv2.app.presentation.artist.model.ArtistUiModel;

public interface ArtistsPresenter {

    void initFlow();

    void onSearch(String textToSearch);

    void onClickArtist(ArtistUiModel artist);

    void onDestroy();
}
