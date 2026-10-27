package es.udc.psi.cleanarchv2.app.data.api.dto;

import com.google.gson.annotations.SerializedName;

public class VenueDto {

    @SerializedName("name")
    private String name;

    @SerializedName("city")
    private CityDto city;

    public VenueDto(String name,
                 CityDto city) {

        this.name = name;
        this.city = city;
    }

    public String getName() {

        return name;
    }

    public CityDto getCity() {

        return city;
    }
}
