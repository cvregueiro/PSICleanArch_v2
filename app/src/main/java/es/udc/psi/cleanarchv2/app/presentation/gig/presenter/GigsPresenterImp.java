package es.udc.psi.cleanarchv2.app.presentation.gig.presenter;

import android.util.Log;

import java.net.HttpURLConnection;
import java.util.List;

import androidx.annotation.NonNull;
import es.udc.psi.cleanarchv2.BuildConfig;
import es.udc.psi.cleanarchv2.app.data.SetListFmAPI;
import es.udc.psi.cleanarchv2.app.data.interceptor.SetListFmApiInterceptor;
import es.udc.psi.cleanarchv2.app.domain.SetList;
import es.udc.psi.cleanarchv2.app.domain.SetLists;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GigsPresenterImp implements GigsPresenter {

    private final static String TAG = GigsPresenterImp.class.getSimpleName();

    private GigsView mView;

    public GigsPresenterImp(GigsView view) {

        mView = view;
    }

    @Override
    public void initFlow(String artistId) {

        if (BuildConfig.SETLISTFM_API_KEY.isEmpty()) {

            mView.showMissingApiKey();
            return;
        }

        mView.showLoading();
        getGigs(artistId);
    }

    @Override
    public void onDestroy() {

        mView = null;
    }

    private void getGigs(String artistId) {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        // Never write the API key to the log
        loggingInterceptor.redactHeader("x-api-key");
        List<Interceptor> interceptors = okHttpClient.interceptors();
        interceptors.add(new SetListFmApiInterceptor());
        interceptors.add(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.setlist.fm/rest/1.0/")
                .client(okHttpClient.build()).addConverterFactory(GsonConverterFactory.create()).build();

        SetListFmAPI api = retrofit.create(SetListFmAPI.class);

        Call<SetLists> call = api.searchSetLists(artistId);

        call.enqueue(new Callback<SetLists>() {

            @Override
            public void onResponse(@NonNull Call<SetLists> call,
                                   @NonNull Response<SetLists> response) {

                if (mView == null) {
                    return;
                }

                if (response.isSuccessful() && response.body() != null) {

                    Log.i(TAG, "Response OK: " + response.code());
                    List<SetList> gigs = response.body().getSetLists();
                    if (gigs == null || gigs.isEmpty()) {
                        mView.showEmptyView();
                    } else {
                        mView.showGigs(gigs);
                    }

                } else if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {

                    // setlist.fm answers 404 when the artist has no setlists
                    Log.i(TAG, "No gigs: " + response.code());
                    mView.showEmptyView();

                } else {

                    Log.e(TAG, "Response fails: " + response.code());
                    mView.showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SetLists> call,
                                  @NonNull Throwable t) {

                Log.e(TAG, "Response fails: " + t.getMessage());
                if (mView != null) {
                    mView.showError();
                }
            }
        });
    }
}
