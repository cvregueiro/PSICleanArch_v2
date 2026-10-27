package es.udc.psi.cleanarchv2.app.domain.gig.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import es.udc.psi.cleanarchv2.app.data.api.SetListFmAPI;
import es.udc.psi.cleanarchv2.app.data.api.dto.GigDtoMapper;
import es.udc.psi.cleanarchv2.app.data.api.dto.SetListsDto;
import es.udc.psi.cleanarchv2.app.domain.exception.ConfigurationException;
import es.udc.psi.cleanarchv2.app.domain.model.Gig;
import retrofit2.Response;

public class GigServiceImp implements GigService {

    private final SetListFmAPI mApi;
    private final String mApiKey;

    public GigServiceImp(SetListFmAPI api,
                         String apiKey) {

        mApi = api;
        mApiKey = apiKey;
    }

    @Override
    public List<Gig> searchGigs(String artistId) throws IOException {

        if (mApiKey == null || mApiKey.isEmpty()) {
            throw new ConfigurationException("setlist.fm API key not found in local.properties");
        }

        Response<SetListsDto> response = mApi.searchSetLists(artistId).execute();

        // setlist.fm answers 404 when the artist has no setlists
        if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {
            return new ArrayList<>();
        }

        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("setlist.fm response fails: " + response.code());
        }

        return new GigDtoMapper(response.body().getSetLists()).map();
    }
}
