package es.udc.psi.cleanarchv2.app.domain.gig;

import java.util.Date;

public class Gig {

    private String id;
    private Date eventDate;
    private String venueName;
    private String cityName;
    private String countryName;
    private String tourName;

    public Gig(String id,
               Date eventDate,
               String venueName,
               String cityName,
               String countryName,
               String tourName) {

        this.id = id;
        this.eventDate = eventDate;
        this.venueName = venueName;
        this.cityName = cityName;
        this.countryName = countryName;
        this.tourName = tourName;
    }

    public String getId() {

        return id;
    }

    public Date getEventDate() {

        return eventDate;
    }

    public String getVenueName() {

        return venueName;
    }

    public String getCityName() {

        return cityName;
    }

    public String getCountryName() {

        return countryName;
    }

    public String getTourName() {

        return tourName;
    }
}
