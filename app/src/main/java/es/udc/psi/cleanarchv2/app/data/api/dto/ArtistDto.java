package es.udc.psi.cleanarchv2.app.data.api.dto;

import com.google.gson.annotations.SerializedName;

public class ArtistDto {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public ArtistDto(String id,
                  String name) {

        this.id = id;
        this.name = name;
    }

    public String getId() {

        return id;
    }

    public String getName() {

        return name;
    }
}
