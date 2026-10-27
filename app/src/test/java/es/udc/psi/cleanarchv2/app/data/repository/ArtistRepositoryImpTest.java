package es.udc.psi.cleanarchv2.app.data.repository;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.udc.psi.cleanarchv2.app.data.local.ArtistLocalDataSource;
import es.udc.psi.cleanarchv2.app.data.remote.ArtistRemoteDataSource;
import es.udc.psi.cleanarchv2.app.data.remote.dto.ArtistDto;
import es.udc.psi.cleanarchv2.app.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.app.domain.model.Artist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArtistRepositoryImpTest {

    private static final List<ArtistDto> RANCID =
            Collections.singletonList(new ArtistDto("24f8d8a5-269b-475c-a1cb-792990b0b2ee", "Rancid"));

    private final FakeArtistLocalDataSource mLocal = new FakeArtistLocalDataSource();

    @Test
    public void withNetwork_asksTheServerAndSavesTheResult() throws Exception {

        ArtistRepositoryImp repository = new ArtistRepositoryImp(text -> RANCID, mLocal, () -> true);

        List<Artist> artists = repository.searchArtists("rancid");

        assertEquals("Rancid", artists.get(0).getName());
        assertEquals(RANCID, mLocal.saved.get("rancid"));
    }

    @Test
    public void withoutNetwork_readsTheSavedResultWithoutAskingTheServer() throws Exception {

        mLocal.saved.put("rancid", RANCID);
        ArtistRemoteDataSource server = text -> {
            throw new AssertionError("The server must not be called without network");
        };
        ArtistRepositoryImp repository = new ArtistRepositoryImp(server, mLocal, () -> false);

        List<Artist> artists = repository.searchArtists("rancid");

        assertEquals("24f8d8a5-269b-475c-a1cb-792990b0b2ee", artists.get(0).getId());
    }

    @Test(expected = DataUnavailableException.class)
    public void withoutNetworkAndWithoutSavedResult_throwsDataUnavailableException() throws Exception {

        new ArtistRepositoryImp(text -> RANCID, mLocal, () -> false).searchArtists("rancid");
    }

    @Test(expected = DataUnavailableException.class)
    public void whenTheServerFails_translatesIOExceptionToDomainException() throws Exception {

        ArtistRepositoryImp repository = new ArtistRepositoryImp(text -> {
            throw new IOException("server busy");
        }, mLocal, () -> true);

        repository.searchArtists("rancid");
    }

    @Test
    public void whenTheCopyCannotBeSaved_stillReturnsTheArtistsOfTheServer() throws Exception {

        mLocal.failOnSave = true;
        ArtistRepositoryImp repository = new ArtistRepositoryImp(text -> RANCID, mLocal, () -> true);

        assertEquals(1, repository.searchArtists("rancid").size());
        assertTrue(mLocal.saved.isEmpty());
    }

    // Local source in memory
    private static class FakeArtistLocalDataSource implements ArtistLocalDataSource {

        private final Map<String, List<ArtistDto>> saved = new HashMap<>();
        private boolean failOnSave;

        @Override
        public List<ArtistDto> getArtists(String textToSearch) throws IOException {

            if (!saved.containsKey(textToSearch)) {
                throw new FileNotFoundException(textToSearch);
            }
            return saved.get(textToSearch);
        }

        @Override
        public void saveArtists(String textToSearch,
                                List<ArtistDto> artists) throws IOException {

            if (failOnSave) {
                throw new IOException("disk full");
            }
            saved.put(textToSearch, artists);
        }
    }
}
