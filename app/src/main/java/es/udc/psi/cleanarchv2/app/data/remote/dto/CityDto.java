package es.udc.psi.cleanarchv2.app.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class CityDto {

    @SerializedName("name")
    private String name;

    @SerializedName("country")
    private CountryDto country;

    public CityDto(String name,
                CountryDto country) {

        this.name = name;
        this.country = country;
    }

    public String getName() {

        return name;
    }

    public CountryDto getCountry() {

        return country;
    }
}
