package es.udc.psi.cleanarchv2.app.data.gig.dto;

import com.google.gson.annotations.SerializedName;

public class CountryDto {

    @SerializedName("name")
    private String name;

    public CountryDto(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }
}
