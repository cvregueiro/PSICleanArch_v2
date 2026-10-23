package es.udc.psi.cleanarchv2.app.presentation.artist.presenter;

import es.udc.psi.cleanarchv2.app.domain.Artist;

public interface ArtistsPresenter {

    void initFlow();

    void onSearch(String textToSearch);

    void onClickArtist(Artist artist);

    void onDestroy();
}
