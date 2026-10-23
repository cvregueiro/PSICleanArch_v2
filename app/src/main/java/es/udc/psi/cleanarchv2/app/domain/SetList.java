package es.udc.psi.cleanarchv2.app.domain;

import com.google.gson.annotations.SerializedName;

public class SetList {

    @SerializedName("id")
    private String id;

    @SerializedName("eventDate")
    private String eventDate;

    @SerializedName("venue")
    private Venue venue;

    @SerializedName("tour")
    private Tour tour;

    public SetList(String id,
                   String eventDate,
                   Venue venue,
                   Tour tour) {

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

    public Venue getVenue() {

        return venue;
    }

    public Tour getTour() {

        return tour;
    }
}
