package es.udc.psi.cleanarchv2.app.presentation.gig.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import es.udc.psi.cleanarchv2.app.domain.model.Gig;

public class GigUiModelMapper {

    private List<Gig> mGigs;

    public GigUiModelMapper(List<Gig> gigs) {

        mGigs = gigs;
    }

    public List<GigUiModel> map() {

        List<GigUiModel> gigs = new ArrayList<>();
        for (Gig gig : mGigs) {
            gigs.add(new GigUiModel(formatDate(gig), valueOrEmpty(gig.getVenueName()), getPlace(gig), gig.getTourName()));
        }
        return gigs;
    }

    private String formatDate(Gig gig) {

        if (gig.getEventDate() == null) {
            return "";
        }
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(gig.getEventDate());
    }

    private String getPlace(Gig gig) {

        String city = valueOrEmpty(gig.getCityName());
        String country = valueOrEmpty(gig.getCountryName());
        if (city.isEmpty() || country.isEmpty()) {
            return city + country;
        }
        return city + ", " + country;
    }

    private String valueOrEmpty(String value) {

        return value != null ? value : "";
    }
}
