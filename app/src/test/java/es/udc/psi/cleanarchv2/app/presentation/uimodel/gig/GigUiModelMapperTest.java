package es.udc.psi.cleanarchv2.app.presentation.uimodel.gig;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import es.udc.psi.cleanarchv2.app.domain.model.Gig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GigUiModelMapperTest {

    @Test
    public void map_buildsThePlaceAndTheTour() {

        List<GigUiModel> gigs = new GigUiModelMapper(Arrays.asList(
                new Gig("1", null, "Petco Park", "San Diego", "United States", "The Saviors Tour"),
                new Gig("2", null, null, "Madrid", null, null))).map();

        assertEquals("San Diego, United States", gigs.get(0).getPlace());
        assertTrue(gigs.get(0).hasTour());

        assertEquals("", gigs.get(1).getVenue());
        assertEquals("Madrid", gigs.get(1).getPlace());
        assertEquals("", gigs.get(1).getDate());
        assertFalse(gigs.get(1).hasTour());
    }
}
