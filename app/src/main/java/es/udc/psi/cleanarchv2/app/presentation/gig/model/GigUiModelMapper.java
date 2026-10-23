package es.udc.psi.cleanarchv2.app.presentation.gig.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.udc.psi.cleanarchv2.app.domain.City;
import es.udc.psi.cleanarchv2.app.domain.SetList;
import es.udc.psi.cleanarchv2.app.domain.Venue;

public class GigUiModelMapper {

    // setlist.fm date format: 28-09-2024
    private static final String API_DATE_FORMAT = "dd-MM-yyyy";

    private List<SetList> mGigs;

    public GigUiModelMapper(List<SetList> gigs) {

        mGigs = gigs;
    }

    public List<GigUiModel> map() {

        List<GigUiModel> gigs = new ArrayList<>();
        for (SetList gig : mGigs) {
            gigs.add(new GigUiModel(formatDate(gig.getEventDate()), getVenue(gig), getPlace(gig), getTour(gig)));
        }
        return gigs;
    }

    private String formatDate(String eventDate) {

        if (eventDate == null) {
            return "";
        }

        try {

            SimpleDateFormat apiFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.ROOT);
            return DateFormat.getDateInstance(DateFormat.MEDIUM).format(apiFormat.parse(eventDate));

        } catch (ParseException e) {
            return eventDate;
        }
    }

    private String getVenue(SetList gig) {

        Venue venue = gig.getVenue();
        return venue != null && venue.getName() != null ? venue.getName() : "";
    }

    private String getPlace(SetList gig) {

        Venue venue = gig.getVenue();
        City city = venue != null ? venue.getCity() : null;
        if (city == null) {
            return "";
        }

        String place = city.getName();
        if (city.getCountry() != null) {
            place = place + ", " + city.getCountry().getName();
        }
        return place;
    }

    private String getTour(SetList gig) {

        return gig.getTour() != null ? gig.getTour().getName() : null;
    }
}
