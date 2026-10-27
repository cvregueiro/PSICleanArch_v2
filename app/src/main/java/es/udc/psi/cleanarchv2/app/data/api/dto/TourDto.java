package es.udc.psi.cleanarchv2.app.data.api.dto;

import com.google.gson.annotations.SerializedName;

public class TourDto {

    @SerializedName("name")
    private String name;

    public TourDto(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }
}
