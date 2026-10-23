package es.udc.psi.cleanarchv2.app.presentation.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import es.udc.psi.cleanarchv2.BuildConfig;
import es.udc.psi.cleanarchv2.R;
import es.udc.psi.cleanarchv2.app.data.MusicBrainzAPI;
import es.udc.psi.cleanarchv2.app.domain.SearchArtists;
import es.udc.psi.cleanarchv2.app.presentation.BaseActivity;
import es.udc.psi.cleanarchv2.app.presentation.main.adapter.ArtistAdapter;
import es.udc.psi.cleanarchv2.databinding.ActivityMainBinding;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ArtistAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setUpView();
    }

    @Override
    protected ActivityMainBinding inflateBinding(LayoutInflater inflater) {

        return ActivityMainBinding.inflate(inflater);
    }

    private void setUpView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.mainList.setLayoutManager(linearLayoutManager);

        mAdapter = new ArtistAdapter();
        mBinding.mainList.setAdapter(mAdapter);

        getArtists();
    }

    private void getArtists() {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        List<Interceptor> interceptors = okHttpClient.interceptors();
        interceptors.add(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://musicbrainz.org/ws/2/").client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create()).build();

        MusicBrainzAPI api = retrofit.create(MusicBrainzAPI.class);

        String query = "artist:rancid";
        String format = "json";
        Call<SearchArtists> call = api.searchArtistByName(query, format);

        call.enqueue(new Callback<SearchArtists>() {

            @Override
            public void onResponse(@NonNull Call<SearchArtists> call,
                                   @NonNull Response<SearchArtists> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.i(TAG, "Response OK: " + response.code());
                    mAdapter.setItems(response.body().getArtists());

                } else {

                    Log.e(TAG, "Response fails: " + response.code());
                }

            }

            @Override
            public void onFailure(@NonNull Call<SearchArtists> call,
                                  @NonNull Throwable t) {

                Log.e(TAG, "Response fails: " + t.getMessage());
                Toast.makeText(MainActivity.this, R.string.error_general, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
