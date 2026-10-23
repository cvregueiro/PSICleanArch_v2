package es.udc.psi.cleanarchv2.app.presentation.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import es.udc.psi.cleanarchv2.app.data.MusicBrainzAPI;
import es.udc.psi.cleanarchv2.app.domain.Artist;
import es.udc.psi.cleanarchv2.app.presentation.BaseActivity;
import es.udc.psi.cleanarchv2.app.presentation.main.adapter.ArtistAdapter;
import es.udc.psi.cleanarchv2.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private static final String TAG = MainActivity.class.getSimpleName();

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

        ArtistAdapter adapter = new ArtistAdapter(getArtists());
        mBinding.mainList.setAdapter(adapter);
    }

    private List<Artist> getArtists() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://musicbrainz.org/ws/2/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        MusicBrainzAPI api = retrofit.create(MusicBrainzAPI.class);

        String query = "artist:rancid";
        String format = "json";
        Call<List<Artist>> call = api.searchArtistByName(query, format);

        try {

            // Synchronous call on the main thread: Android throws a NetworkOnMainThreadException.
            // It is solved in the next feature with an asynchronous call.
            return call.execute().body();

        } catch (IOException | RuntimeException e) {
            Log.e(TAG, "Response fails", e);
        }

        return new ArrayList<>();
    }
}
