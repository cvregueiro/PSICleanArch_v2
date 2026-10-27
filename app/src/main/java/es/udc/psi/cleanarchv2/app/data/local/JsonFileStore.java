package es.udc.psi.cleanarchv2.app.data.local;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * Saves objects as JSON files in a directory of the device, one file per key.
 * It only uses java.io and Gson, so it can be tested in the JVM with a temporary folder.
 */
public class JsonFileStore {

    private static final int MAX_NAME_LENGTH = 50;

    private final File mDirectory;
    private final Gson mGson = new Gson();

    public JsonFileStore(File directory) {

        mDirectory = directory;
    }

    public void write(String key,
                      Object data) throws IOException {

        if (!mDirectory.isDirectory() && !mDirectory.mkdirs()) {
            throw new IOException("Cannot create the directory " + mDirectory);
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(fileOf(key)), StandardCharsets.UTF_8)) {
            mGson.toJson(data, writer);
        }
    }

    /**
     * Reads the object saved with the key.
     * Throws FileNotFoundException if nothing has been saved with that key.
     */
    public <T> T read(String key,
                      Type type) throws IOException {

        try (Reader reader = new InputStreamReader(new FileInputStream(fileOf(key)), StandardCharsets.UTF_8)) {
            T data = mGson.fromJson(reader, type);
            if (data == null) {
                throw new IOException("Empty file for " + key);
            }
            return data;
        } catch (JsonParseException e) {
            throw new IOException("Corrupted file for " + key, e);
        }
    }

    // Readable name (the key without special characters) plus the hash of the key, so that it is unique
    private File fileOf(String key) {

        String name = key.replaceAll("[^A-Za-z0-9]+", "_");
        if (name.length() > MAX_NAME_LENGTH) {
            name = name.substring(0, MAX_NAME_LENGTH);
        }
        return new File(mDirectory, name + "_" + Integer.toHexString(key.hashCode()) + ".json");
    }
}
