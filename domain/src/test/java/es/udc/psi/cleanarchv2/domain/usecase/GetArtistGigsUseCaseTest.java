package es.udc.psi.cleanarchv2.domain.usecase;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import es.udc.psi.cleanarchv2.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.domain.model.Gig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetArtistGigsUseCaseTest {

    @Test
    public void execute_returnsTheGigsOfTheRepository() throws Exception {

        GetArtistGigsUseCase useCase = new GetArtistGigsUseCase(artistId -> Collections.singletonList(
                new Gig("1", null, "Petco Park", "San Diego", "United States", null)));

        List<Gig> gigs = useCase.execute("24f8d8a5-269b-475c-a1cb-792990b0b2ee");

        assertEquals("Petco Park", gigs.get(0).getVenueName());
    }

    @Test
    public void execute_withoutArtist_returnsEmptyList() throws Exception {

        GetArtistGigsUseCase useCase = new GetArtistGigsUseCase(artistId -> {
            throw new AssertionError("The repository must not be called");
        });

        assertTrue(useCase.execute("").isEmpty());
    }

    @Test(expected = DataUnavailableException.class)
    public void execute_propagatesTheErrorsOfTheRepository() throws Exception {

        GetArtistGigsUseCase useCase = new GetArtistGigsUseCase(artistId -> {
            throw new DataUnavailableException("network down");
        });

        useCase.execute("24f8d8a5-269b-475c-a1cb-792990b0b2ee");
    }
}
