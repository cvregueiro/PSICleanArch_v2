package es.udc.psi.cleanarchv2.data.remote.dto;

import java.util.ArrayList;
import java.util.List;

import es.udc.psi.cleanarchv2.domain.model.Artist;

public class ArtistDtoMapper {

    private List<ArtistDto> mArtists;

    public ArtistDtoMapper(List<ArtistDto> artists) {

        mArtists = artists;
    }

    public List<Artist> map() {

        List<Artist> artists = new ArrayList<>();
        if (mArtists == null) {
            return artists;
        }

        for (ArtistDto artist : mArtists) {
            artists.add(new Artist(artist.getId(), artist.getName()));
        }
        return artists;
    }
}
