package es.udc.psi.cleanarchv2.app.presentation.artist.presenter;

import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import es.udc.psi.cleanarchv2.BuildConfig;
import es.udc.psi.cleanarchv2.app.data.MusicBrainzAPI;
import es.udc.psi.cleanarchv2.app.data.interceptor.MusicBrainzApiInterceptor;
import es.udc.psi.cleanarchv2.app.domain.Artist;
import es.udc.psi.cleanarchv2.app.domain.SearchArtists;
import es.udc.psi.cleanarchv2.app.presentation.artist.model.ArtistUiModel;
import es.udc.psi.cleanarchv2.app.presentation.artist.model.ArtistUiModelMapper;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArtistsPresenterImp implements ArtistsPresenter {

    private final static String TAG = ArtistsPresenterImp.class.getSimpleName();

    private ArtistsView mView;

    public ArtistsPresenterImp(ArtistsView view) {

        mView = view;
    }

    @Override
    public void initFlow() {

        mView.showEmptyView();
    }

    @Override
    public void onSearch(String textToSearch) {

        String text = textToSearch.trim();
        if (text.isEmpty()) {

            mView.showEmptyQueryError();
            return;
        }

        mView.hideQueryError();
        mView.hideKeyboard();
        mView.showLoading();
        getArtists(text);
    }

    @Override
    public void onClickArtist(ArtistUiModel artist) {

        mView.navigateToGigs(artist.getId(), artist.getName());
    }

    @Override
    public void onDestroy() {

        mView = null;
    }

    private void getArtists(String textToSearch) {

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

                if (mView == null) {
                    return;
                }

                if (response.isSuccessful() && response.body() != null) {

                    Log.i(TAG, "Response OK: " + response.code());
                    List<Artist> artists = response.body().getArtists();
                    if (artists == null || artists.isEmpty()) {
                        mView.showNoResults();
                    } else {
                        mView.showArtists(getArtistsUiModel(artists));
                    }

                } else {

                    Log.e(TAG, "Response fails: " + response.code());
                    mView.showError();
                }

            }

            @Override
            public void onFailure(@NonNull Call<SearchArtists> call,
                                  @NonNull Throwable t) {

                Log.e(TAG, "Response fails: " + t.getMessage());
                if (mView != null) {
                    mView.showError();
                }
            }
        });
    }

    private List<ArtistUiModel> getArtistsUiModel(List<Artist> artists) {

        return new ArtistUiModelMapper(artists).map();
    }

    // Lucene query of MusicBrainz: the text is searched as a phrase in the artist field
    private String buildQuery(String textToSearch) {

        String phrase = textToSearch.replace("\\", "\\\\").replace("\"", "\\\"");
        return "artist:\"" + phrase + "\"";
    }
}
