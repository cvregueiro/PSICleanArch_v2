package es.udc.psi.cleanarchv2.app.presentation.uimodel.artist;

import java.util.ArrayList;
import java.util.List;

import es.udc.psi.cleanarchv2.domain.model.Artist;

public class ArtistUiModelMapper {

    private List<Artist> mArtists;

    public ArtistUiModelMapper(List<Artist> artists) {

        mArtists = artists;
    }

    public List<ArtistUiModel> map() {

        List<ArtistUiModel> artists = new ArrayList<>();
        for (Artist artist : mArtists) {
            artists.add(new ArtistUiModel(artist.getId(), artist.getName()));
        }
        return artists;
    }
}
