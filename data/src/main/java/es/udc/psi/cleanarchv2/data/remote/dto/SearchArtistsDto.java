package es.udc.psi.cleanarchv2.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchArtistsDto {

    @SerializedName("count")
    private int count;

    @SerializedName("artists")
    private List<ArtistDto> artists;

    public SearchArtistsDto(int count,
                         List<ArtistDto> artists) {

        this.count = count;
        this.artists = artists;
    }

    public int getCount() {

        return count;
    }

    public List<ArtistDto> getArtists() {

        return artists;
    }
}
