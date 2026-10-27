package es.udc.psi.cleanarchv2.app.presentation.presenter.artist;

import es.udc.psi.cleanarchv2.app.presentation.uimodel.artist.ArtistUiModel;

public interface ArtistsPresenter {

    void initFlow();

    void onSearch(String textToSearch);

    void onClickArtist(ArtistUiModel artist);

    void onDestroy();
}
