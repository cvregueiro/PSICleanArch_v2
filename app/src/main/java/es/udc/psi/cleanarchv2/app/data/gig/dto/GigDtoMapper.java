package es.udc.psi.cleanarchv2.app.data.gig.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.udc.psi.cleanarchv2.app.domain.gig.Gig;

public class GigDtoMapper {

    // setlist.fm date format: 28-09-2024
    private static final String API_DATE_FORMAT = "dd-MM-yyyy";

    private List<SetListDto> mSetLists;

    public GigDtoMapper(List<SetListDto> setLists) {

        mSetLists = setLists;
    }

    public List<Gig> map() {

        List<Gig> gigs = new ArrayList<>();
        if (mSetLists == null) {
            return gigs;
        }

        for (SetListDto setList : mSetLists) {

            VenueDto venue = setList.getVenue();
            CityDto city = venue != null ? venue.getCity() : null;
            CountryDto country = city != null ? city.getCountry() : null;
            TourDto tour = setList.getTour();

            gigs.add(new Gig(setList.getId(),
                             parseDate(setList.getEventDate()),
                             venue != null ? venue.getName() : null,
                             city != null ? city.getName() : null,
                             country != null ? country.getName() : null,
                             tour != null ? tour.getName() : null));
        }
        return gigs;
    }

    private Date parseDate(String eventDate) {

        if (eventDate == null) {
            return null;
        }

        try {
            return new SimpleDateFormat(API_DATE_FORMAT, Locale.ROOT).parse(eventDate);
        } catch (ParseException e) {
            return null;
        }
    }
}
