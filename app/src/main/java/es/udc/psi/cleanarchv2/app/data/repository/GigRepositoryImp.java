package es.udc.psi.cleanarchv2.app.data.repository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import es.udc.psi.cleanarchv2.app.data.api.SetListFmAPI;
import es.udc.psi.cleanarchv2.app.data.api.dto.GigDtoMapper;
import es.udc.psi.cleanarchv2.app.data.api.dto.SetListsDto;
import es.udc.psi.cleanarchv2.app.domain.exception.ConfigurationException;
import es.udc.psi.cleanarchv2.app.domain.exception.DataUnavailableException;
import es.udc.psi.cleanarchv2.app.domain.model.Gig;
import es.udc.psi.cleanarchv2.app.domain.repository.GigRepository;
import retrofit2.Response;

/**
 * Adapter of the GigRepository port: gets the setlists of setlist.fm,
 * maps them to gigs and translates the errors to domain exceptions.
 */
public class GigRepositoryImp implements GigRepository {

    private final SetListFmAPI mApi;
    private final String mApiKey;

    public GigRepositoryImp(SetListFmAPI api,
                            String apiKey) {

        mApi = api;
        mApiKey = apiKey;
    }

    @Override
    public List<Gig> getGigs(String artistId) throws DataUnavailableException {

        if (mApiKey == null || mApiKey.isEmpty()) {
            throw new ConfigurationException("setlist.fm API key not found in local.properties");
        }

        try {
            Response<SetListsDto> response = mApi.searchSetLists(artistId).execute();

            // setlist.fm answers 404 when the artist has no setlists
            if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                return new ArrayList<>();
            }

            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("setlist.fm response fails: " + response.code());
            }

            return new GigDtoMapper(response.body().getSetLists()).map();
        } catch (IOException e) {
            throw new DataUnavailableException("Gigs not available", e);
        }
    }
}
