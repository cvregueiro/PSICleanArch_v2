package es.udc.psi.cleanarchv2.app.domain;

import com.google.gson.annotations.SerializedName;

public class Venue {

    @SerializedName("name")
    private String name;

    @SerializedName("city")
    private City city;

    public Venue(String name,
                 City city) {

        this.name = name;
        this.city = city;
    }

    public String getName() {

        return name;
    }

    public City getCity() {

        return city;
    }
}
