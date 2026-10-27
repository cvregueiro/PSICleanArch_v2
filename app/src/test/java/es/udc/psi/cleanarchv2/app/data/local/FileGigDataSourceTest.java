package es.udc.psi.cleanarchv2.app.data.local;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.Collections;
import java.util.List;

import es.udc.psi.cleanarchv2.app.data.remote.dto.CityDto;
import es.udc.psi.cleanarchv2.app.data.remote.dto.CountryDto;
import es.udc.psi.cleanarchv2.app.data.remote.dto.GigDtoMapper;
import es.udc.psi.cleanarchv2.app.data.remote.dto.SetListDto;
import es.udc.psi.cleanarchv2.app.data.remote.dto.TourDto;
import es.udc.psi.cleanarchv2.app.data.remote.dto.VenueDto;
import es.udc.psi.cleanarchv2.domain.model.Gig;

import static org.junit.Assert.assertEquals;

public class FileGigDataSourceTest {

    @Rule
    public TemporaryFolder mFolder = new TemporaryFolder();

    @Test
    public void getSetLists_returnsTheSavedSetListsWithAllTheirData() throws Exception {

        FileGigDataSource dataSource = new FileGigDataSource(new JsonFileStore(mFolder.getRoot()));
        VenueDto venue = new VenueDto("Petco Park", new CityDto("San Diego", new CountryDto("United States")));
        dataSource.saveSetLists("artist", Collections.singletonList(
                new SetListDto("1", "28-09-2024", venue, new TourDto("The Saviors Tour"))));

        List<Gig> gigs = new GigDtoMapper(dataSource.getSetLists("artist")).map();

        assertEquals("Petco Park", gigs.get(0).getVenueName());
        assertEquals("San Diego", gigs.get(0).getCityName());
        assertEquals("The Saviors Tour", gigs.get(0).getTourName());
    }
}
