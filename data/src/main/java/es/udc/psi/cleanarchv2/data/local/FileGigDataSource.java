package es.udc.psi.cleanarchv2.data.local;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import es.udc.psi.cleanarchv2.data.remote.dto.SetListDto;

/**
 * Setlists saved in JSON files, one file per artist.
 */
public class FileGigDataSource implements GigLocalDataSource {

    private static final Type SETLISTS_TYPE = new TypeToken<List<SetListDto>>() { }.getType();

    private final JsonFileStore mStore;

    public FileGigDataSource(JsonFileStore store) {

        mStore = store;
    }

    @Override
    public List<SetListDto> getSetLists(String artistId) throws IOException {

        return mStore.read(keyOf(artistId), SETLISTS_TYPE);
    }

    @Override
    public void saveSetLists(String artistId,
                             List<SetListDto> setLists) throws IOException {

        mStore.write(keyOf(artistId), setLists);
    }

    private String keyOf(String artistId) {

        return "gigs_" + artistId;
    }
}
