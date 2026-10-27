package es.udc.psi.cleanarchv2.app.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class SetListDto {

    @SerializedName("id")
    private String id;

    @SerializedName("eventDate")
    private String eventDate;

    @SerializedName("venue")
    private VenueDto venue;

    @SerializedName("tour")
    private TourDto tour;

    public SetListDto(String id,
                   String eventDate,
                   VenueDto venue,
                   TourDto tour) {

        this.id = id;
        this.eventDate = eventDate;
        this.venue = venue;
        this.tour = tour;
    }

    public String getId() {

        return id;
    }

    public String getEventDate() {

        return eventDate;
    }

    public VenueDto getVenue() {

        return venue;
    }

    public TourDto getTour() {

        return tour;
    }
}
