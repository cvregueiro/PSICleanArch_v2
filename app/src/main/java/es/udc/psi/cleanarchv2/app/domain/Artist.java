package es.udc.psi.cleanarchv2.app.domain;

import com.google.gson.annotations.SerializedName;

public class Artist {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public Artist(String id,
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
