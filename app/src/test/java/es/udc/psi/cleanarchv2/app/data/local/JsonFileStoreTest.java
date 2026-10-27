package es.udc.psi.cleanarchv2.app.data.local;

import com.google.gson.reflect.TypeToken;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonFileStoreTest {

    private static final Type STRINGS_TYPE = new TypeToken<List<String>>() { }.getType();

    @Rule
    public TemporaryFolder mFolder = new TemporaryFolder();

    @Test
    public void read_returnsWhatWasWritten() throws Exception {

        JsonFileStore store = new JsonFileStore(new File(mFolder.getRoot(), "offline"));

        store.write("artists_rancid", Arrays.asList("Rancid", "Rancid Hell Spawn"));

        assertEquals(Arrays.asList("Rancid", "Rancid Hell Spawn"), store.read("artists_rancid", STRINGS_TYPE));
    }

    @Test(expected = FileNotFoundException.class)
    public void read_withoutSavedData_throwsFileNotFoundException() throws Exception {

        new JsonFileStore(mFolder.getRoot()).read("artists_nothing", STRINGS_TYPE);
    }

    @Test
    public void keysWithTheSameReadableName_doNotShareTheFile() throws Exception {

        JsonFileStore store = new JsonFileStore(mFolder.getRoot());

        store.write("artists_ac/dc", Collections.singletonList("AC/DC"));
        store.write("artists_ac dc", Collections.singletonList("Other"));

        assertEquals(Collections.singletonList("AC/DC"), store.read("artists_ac/dc", STRINGS_TYPE));
    }
}
