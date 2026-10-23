package es.udc.psi.cleanarchv2.app.data.gig.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SetListsDto {

    @SerializedName("setlist")
    private List<SetListDto> setLists;

    public SetListsDto(List<SetListDto> setLists) {

        this.setLists = setLists;
    }

    public List<SetListDto> getSetLists() {

        return setLists;
    }
}
