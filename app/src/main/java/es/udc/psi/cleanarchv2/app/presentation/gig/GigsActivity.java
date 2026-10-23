package es.udc.psi.cleanarchv2.app.presentation.gig;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import es.udc.psi.cleanarchv2.BuildConfig;
import es.udc.psi.cleanarchv2.R;
import es.udc.psi.cleanarchv2.app.data.SetListFmAPI;
import es.udc.psi.cleanarchv2.app.data.interceptor.SetListFmApiInterceptor;
import es.udc.psi.cleanarchv2.app.domain.SetList;
import es.udc.psi.cleanarchv2.app.domain.SetLists;
import es.udc.psi.cleanarchv2.app.presentation.BaseActivity;
import es.udc.psi.cleanarchv2.app.presentation.gig.adapter.GigAdapter;
import es.udc.psi.cleanarchv2.databinding.ActivityGigsBinding;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GigsActivity extends BaseActivity<ActivityGigsBinding> {

    private static final String TAG = GigsActivity.class.getSimpleName();

    private static final String EXTRA_ARTIST_ID = "extra_artist_id";
    private static final String EXTRA_ARTIST_NAME = "extra_artist_name";

    private GigAdapter mAdapter;

    public static Intent newIntent(Context context,
                                   String artistId,
                                   String artistName) {

        Intent intent = new Intent(context, GigsActivity.class);
        intent.putExtra(EXTRA_ARTIST_ID, artistId);
        intent.putExtra(EXTRA_ARTIST_NAME, artistName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setUpView();

        getGigs(getIntent().getStringExtra(EXTRA_ARTIST_ID));
    }

    @Override
    protected ActivityGigsBinding inflateBinding(LayoutInflater inflater) {

        return ActivityGigsBinding.inflate(inflater);
    }

    @Override
    public boolean onSupportNavigateUp() {

        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    private void setUpView() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getIntent().getStringExtra(EXTRA_ARTIST_NAME));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.gigsList.setLayoutManager(linearLayoutManager);
        mBinding.gigsList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAdapter = new GigAdapter();
        mBinding.gigsList.setAdapter(mAdapter);
    }

    private void getGigs(String artistId) {

        if (BuildConfig.SETLISTFM_API_KEY.isEmpty()) {

            showEmptyView(R.string.gigs_no_api_key);
            return;
        }

        showLoading();

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

                if (response.isSuccessful() && response.body() != null) {

                    Log.i(TAG, "Response OK: " + response.code());
                    List<SetList> gigs = response.body().getSetLists();
                    if (gigs == null || gigs.isEmpty()) {
                        showEmptyView(R.string.gigs_no_results);
                    } else {
                        showGigs(gigs);
                    }

                } else if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {

                    // setlist.fm answers 404 when the artist has no setlists
                    Log.i(TAG, "No gigs: " + response.code());
                    showEmptyView(R.string.gigs_no_results);

                } else {

                    Log.e(TAG, "Response fails: " + response.code());
                    showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SetLists> call,
                                  @NonNull Throwable t) {

                Log.e(TAG, "Response fails: " + t.getMessage());
                showError();
            }
        });
    }

    private void showLoading() {

        mBinding.gigsProgress.setVisibility(View.VISIBLE);
        mBinding.gigsEmptyView.setVisibility(View.GONE);
        mBinding.gigsList.setVisibility(View.GONE);
    }

    private void showGigs(List<SetList> gigs) {

        mAdapter.setItems(gigs);
        mBinding.gigsProgress.setVisibility(View.GONE);
        mBinding.gigsEmptyView.setVisibility(View.GONE);
        mBinding.gigsList.setVisibility(View.VISIBLE);
    }

    private void showEmptyView(@StringRes int message) {

        mBinding.gigsEmptyView.setText(message);
        mBinding.gigsProgress.setVisibility(View.GONE);
        mBinding.gigsEmptyView.setVisibility(View.VISIBLE);
        mBinding.gigsList.setVisibility(View.GONE);
    }

    private void showError() {

        showEmptyView(R.string.gigs_no_results);
        Toast.makeText(this, R.string.error_general, Toast.LENGTH_SHORT).show();
    }
}
