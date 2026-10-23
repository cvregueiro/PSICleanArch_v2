package es.udc.psi.cleanarchv2.app.presentation.main;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import es.udc.psi.cleanarchv2.BuildConfig;
import es.udc.psi.cleanarchv2.R;
import es.udc.psi.cleanarchv2.app.data.MusicBrainzAPI;
import es.udc.psi.cleanarchv2.app.data.interceptor.MusicBrainzApiInterceptor;
import es.udc.psi.cleanarchv2.app.domain.Artist;
import es.udc.psi.cleanarchv2.app.domain.SearchArtists;
import es.udc.psi.cleanarchv2.app.presentation.BaseActivity;
import es.udc.psi.cleanarchv2.app.presentation.gig.GigsActivity;
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

public class MainActivity extends BaseActivity<ActivityMainBinding> implements ArtistAdapter.OnArtistClickListener {

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

    @Override
    public void onArtistClick(Artist artist) {

        startActivity(GigsActivity.newIntent(this, artist.getId(), artist.getName()));
    }

    private void setUpView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.mainList.setLayoutManager(linearLayoutManager);

        mAdapter = new ArtistAdapter(this);
        mBinding.mainList.setAdapter(mAdapter);

        mBinding.mainSearchButton.setOnClickListener(v -> searchArtists());
        mBinding.mainSearchText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchArtists();
                return true;
            }
            return false;
        });

        showEmptyView(R.string.artists_empty_view);
    }

    private void searchArtists() {

        Editable text = mBinding.mainSearchText.getText();
        String textToSearch = text != null ? text.toString().trim() : "";

        if (textToSearch.isEmpty()) {

            mBinding.mainSearchLayout.setError(getString(R.string.artists_search_empty));
            return;
        }

        mBinding.mainSearchLayout.setError(null);
        hideKeyboard();
        getArtists(textToSearch);
    }

    private void getArtists(String textToSearch) {

        showLoading();

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        List<Interceptor> interceptors = okHttpClient.interceptors();
        interceptors.add(loggingInterceptor);
        interceptors.add(new MusicBrainzApiInterceptor());

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://musicbrainz.org/ws/2/").client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create()).build();

        MusicBrainzAPI api = retrofit.create(MusicBrainzAPI.class);

        String query = buildQuery(textToSearch);
        String format = "json";
        Call<SearchArtists> call = api.searchArtistByName(query, format);

        call.enqueue(new Callback<SearchArtists>() {

            @Override
            public void onResponse(@NonNull Call<SearchArtists> call,
                                   @NonNull Response<SearchArtists> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.i(TAG, "Response OK: " + response.code());
                    List<Artist> artists = response.body().getArtists();
                    if (artists == null || artists.isEmpty()) {
                        showEmptyView(R.string.artists_no_results);
                    } else {
                        showArtists(artists);
                    }

                } else {

                    Log.e(TAG, "Response fails: " + response.code());
                    showError();
                }

            }

            @Override
            public void onFailure(@NonNull Call<SearchArtists> call,
                                  @NonNull Throwable t) {

                Log.e(TAG, "Response fails: " + t.getMessage());
                showError();
            }
        });
    }

    // Lucene query of MusicBrainz: the text is searched as a phrase in the artist field
    private String buildQuery(String textToSearch) {

        String phrase = textToSearch.replace("\\", "\\\\").replace("\"", "\\\"");
        return "artist:\"" + phrase + "\"";
    }

    private void showLoading() {

        mBinding.mainProgress.setVisibility(View.VISIBLE);
        mBinding.mainEmptyView.setVisibility(View.GONE);
        mBinding.mainList.setVisibility(View.GONE);
    }

    private void showArtists(List<Artist> artists) {

        mAdapter.setItems(artists);
        mBinding.mainList.scrollToPosition(0);
        mBinding.mainProgress.setVisibility(View.GONE);
        mBinding.mainEmptyView.setVisibility(View.GONE);
        mBinding.mainList.setVisibility(View.VISIBLE);
    }

    private void showEmptyView(@StringRes int message) {

        mBinding.mainEmptyView.setText(message);
        mBinding.mainProgress.setVisibility(View.GONE);
        mBinding.mainEmptyView.setVisibility(View.VISIBLE);
        mBinding.mainList.setVisibility(View.GONE);
    }

    private void showError() {

        showEmptyView(R.string.artists_no_results);
        Toast.makeText(this, R.string.error_general, Toast.LENGTH_SHORT).show();
    }

    private void hideKeyboard() {

        WindowCompat.getInsetsController(getWindow(), mBinding.mainSearchText).hide(WindowInsetsCompat.Type.ime());
    }
}
