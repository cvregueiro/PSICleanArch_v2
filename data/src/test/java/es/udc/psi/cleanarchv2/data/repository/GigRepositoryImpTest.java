package es.udc.psi.cleanarchv2.data.repository;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.udc.psi.cleanarchv2.data.local.GigLocalDataSource;
import es.udc.psi.cleanarchv2.data.remote.MissingApiKeyException;
import es.udc.psi.cleanarchv2.data.remote.dto.SetListDto;
import es.udc.psi.cleanarchv2.domain.exception.ConfigurationException;
import es.udc.psi.cleanarchv2.domain.exception.DataUnavailableException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GigRepositoryImpTest {

    private final FakeGigLocalDataSource mLocal = new FakeGigLocalDataSource();

    @Test
    public void withNetwork_withoutSetLists_returnsEmptyListAndSavesIt() throws Exception {

        GigRepositoryImp repository = new GigRepositoryImp(artistId -> new ArrayList<>(), mLocal, () -> true);

        assertTrue(repository.getGigs("artist").isEmpty());
        assertTrue(mLocal.saved.containsKey("artist"));
    }

    @Test
    public void withoutNetwork_readsTheSavedSetLists() throws Exception {

        mLocal.saved.put("artist", Collections.singletonList(new SetListDto("1", "28-09-2024", null, null)));
        GigRepositoryImp repository = new GigRepositoryImp(artistId -> {
            throw new AssertionError("The server must not be called without network");
        }, mLocal, () -> false);

        assertEquals("1", repository.getGigs("artist").get(0).getId());
    }

    @Test(expected = ConfigurationException.class)
    public void withoutApiKey_throwsConfigurationException() throws Exception {

        GigRepositoryImp repository = new GigRepositoryImp(artistId -> {
            throw new MissingApiKeyException();
        }, mLocal, () -> true);

        repository.getGigs("artist");
    }

    @Test
    public void whenTheNetworkFails_throwsDataUnavailableException() {

        GigRepositoryImp repository = new GigRepositoryImp(artistId -> {
            throw new IOException("network down");
        }, mLocal, () -> true);

        try {
            repository.getGigs("artist");
        } catch (DataUnavailableException e) {
            assertFalse(e instanceof ConfigurationException);
            return;
        }
        throw new AssertionError("DataUnavailableException expected");
    }

    // Local source in memory
    private static class FakeGigLocalDataSource implements GigLocalDataSource {

        private final Map<String, List<SetListDto>> saved = new HashMap<>();

        @Override
        public List<SetListDto> getSetLists(String artistId) throws IOException {

            if (!saved.containsKey(artistId)) {
                throw new FileNotFoundException(artistId);
            }
            return saved.get(artistId);
        }

        @Override
        public void saveSetLists(String artistId,
                                 List<SetListDto> setLists) {

            saved.put(artistId, setLists);
        }
    }
}
