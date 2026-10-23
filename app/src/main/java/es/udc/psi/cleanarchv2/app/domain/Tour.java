package es.udc.psi.cleanarchv2.app.domain;

import com.google.gson.annotations.SerializedName;

public class Tour {

    @SerializedName("name")
    private String name;

    public Tour(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }
}
