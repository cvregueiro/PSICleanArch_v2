package es.udc.psi.cleanarchv2.app.data.remote.dto;

import org.junit.Test;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import es.udc.psi.cleanarchv2.domain.model.Gig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GigDtoMapperTest {

    @Test
    public void map_flattensTheSetListAndParsesTheDate() {

        VenueDto venue = new VenueDto("Petco Park", new CityDto("San Diego", new CountryDto("United States")));
        SetListDto setList = new SetListDto("1", "28-09-2024", venue, new TourDto("The Saviors Tour"));

        List<Gig> gigs = new GigDtoMapper(Collections.singletonList(setList)).map();

        Gig gig = gigs.get(0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(gig.getEventDate());
        assertEquals(2024, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.SEPTEMBER, calendar.get(Calendar.MONTH));
        assertEquals(28, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals("Petco Park", gig.getVenueName());
        assertEquals("San Diego", gig.getCityName());
        assertEquals("United States", gig.getCountryName());
        assertEquals("The Saviors Tour", gig.getTourName());
    }

    @Test
    public void map_withoutVenueNorTour_keepsNullValues() {

        SetListDto setList = new SetListDto("2", "not a date", null, null);

        Gig gig = new GigDtoMapper(Collections.singletonList(setList)).map().get(0);

        assertNull(gig.getEventDate());
        assertNull(gig.getVenueName());
        assertNull(gig.getCityName());
        assertNull(gig.getTourName());
    }
}
