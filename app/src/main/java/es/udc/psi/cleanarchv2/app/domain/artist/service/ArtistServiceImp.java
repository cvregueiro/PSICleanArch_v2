package es.udc.psi.cleanarchv2.app.domain.artist.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.udc.psi.cleanarchv2.app.domain.artist.Artist;
import es.udc.psi.cleanarchv2.app.domain.artist.datasource.ArtistDatasource;

public class ArtistServiceImp implements ArtistService {

    private final ArtistDatasource mDatasource;

    public ArtistServiceImp(ArtistDatasource artistDatasource) {

        mDatasource = artistDatasource;
    }

    @Override
    public List<Artist> searchArtists(String textToSearch) throws IOException {

        if (textToSearch == null || textToSearch.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return mDatasource.searchArtists(textToSearch.trim());
    }
}
