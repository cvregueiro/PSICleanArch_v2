package es.udc.psi.cleanarchv2.app.data.remote;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import es.udc.psi.cleanarchv2.app.data.remote.api.SetListFmAPI;
import es.udc.psi.cleanarchv2.app.data.remote.dto.SetListDto;
import es.udc.psi.cleanarchv2.app.data.remote.dto.SetListsDto;
import retrofit2.Response;

/**
 * Setlists of setlist.fm, obtained with Retrofit.
 */
public class SetListFmGigDataSource implements GigRemoteDataSource {

    private final SetListFmAPI mApi;
    private final String mApiKey;

    public SetListFmGigDataSource(SetListFmAPI api,
                                  String apiKey) {

        mApi = api;
        mApiKey = apiKey;
    }

    @Override
    public List<SetListDto> searchSetLists(String artistId) throws IOException {

        if (mApiKey == null || mApiKey.isEmpty()) {
            throw new MissingApiKeyException();
        }

        Response<SetListsDto> response = mApi.searchSetLists(artistId).execute();

        // setlist.fm answers 404 when the artist has no setlists
        if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {
            return new ArrayList<>();
        }

        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("setlist.fm response fails: " + response.code());
        }

        List<SetListDto> setLists = response.body().getSetLists();
        return setLists != null ? setLists : new ArrayList<>();
    }
}
