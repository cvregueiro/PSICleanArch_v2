package es.udc.psi.cleanarchv2.app.data;

import es.udc.psi.cleanarchv2.app.domain.SetLists;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SetListFmAPI {

    // https://api.setlist.fm/rest/1.0/search/setlists?artistMbid=24f8d8a5-269b-475c-a1cb-792990b0b2ee&p=1
    @GET("search/setlists")
    Call<SetLists> searchSetLists(@Query("artistMbid") String artistId);
}
