package es.udc.psi.cleanarchv2.app.data.local;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import es.udc.psi.cleanarchv2.app.data.remote.dto.ArtistDto;

/**
 * Artists saved in JSON files, one file per search.
 */
public class FileArtistDataSource implements ArtistLocalDataSource {

    private static final Type ARTISTS_TYPE = new TypeToken<List<ArtistDto>>() { }.getType();

    private final JsonFileStore mStore;

    public FileArtistDataSource(JsonFileStore store) {

        mStore = store;
    }

    @Override
    public List<ArtistDto> getArtists(String textToSearch) throws IOException {

        return mStore.read(keyOf(textToSearch), ARTISTS_TYPE);
    }

    @Override
    public void saveArtists(String textToSearch,
                            List<ArtistDto> artists) throws IOException {

        mStore.write(keyOf(textToSearch), artists);
    }

    // MusicBrainz ignores the case, so "Rancid" and "rancid" are the same search
    private String keyOf(String textToSearch) {

        return "artists_" + textToSearch.toLowerCase(Locale.ROOT);
    }
}
