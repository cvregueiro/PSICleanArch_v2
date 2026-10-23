package es.udc.psi.cleanarchv2.app.domain;

import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("name")
    private String name;

    @SerializedName("country")
    private Country country;

    public City(String name,
                Country country) {

        this.name = name;
        this.country = country;
    }

    public String getName() {

        return name;
    }

    public Country getCountry() {

        return country;
    }
}
