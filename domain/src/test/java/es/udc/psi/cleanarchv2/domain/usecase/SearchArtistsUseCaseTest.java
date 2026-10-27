package es.udc.psi.cleanarchv2.domain.usecase;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import es.udc.psi.cleanarchv2.domain.model.Artist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class SearchArtistsUseCaseTest {

    private String mSearchedText;

    private final SearchArtistsUseCase mUseCase = new SearchArtistsUseCase(textToSearch -> {
        mSearchedText = textToSearch;
        return Collections.singletonList(new Artist("24f8d8a5-269b-475c-a1cb-792990b0b2ee", "Rancid"));
    });

    @Test
    public void execute_trimsTheTextAndAsksTheRepository() throws Exception {

        List<Artist> artists = mUseCase.execute("  rancid ");

        assertEquals("rancid", mSearchedText);
        assertEquals(1, artists.size());
        assertEquals("Rancid", artists.get(0).getName());
    }

    @Test
    public void execute_withBlankText_doesNotAskTheRepository() throws Exception {

        List<Artist> artists = mUseCase.execute("   ");

        assertNull(mSearchedText);
        assertTrue(artists.isEmpty());
    }
}
