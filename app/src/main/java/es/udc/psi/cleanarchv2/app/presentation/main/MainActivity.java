package es.udc.psi.cleanarchv2.app.presentation.main;

import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import es.udc.psi.cleanarchv2.app.domain.Artist;
import es.udc.psi.cleanarchv2.app.presentation.BaseActivity;
import es.udc.psi.cleanarchv2.app.presentation.main.adapter.ArtistAdapter;
import es.udc.psi.cleanarchv2.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

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

        List<Artist> artists = new ArrayList<>();

        artists.add(new Artist(1, "Ben Harper"));
        artists.add(new Artist(2, "Bon Iver"));
        artists.add(new Artist(3, "The National"));
        artists.add(new Artist(4, "Rancid"));
        artists.add(new Artist(5, "The Black Crows"));
        artists.add(new Artist(6, "The Band"));

        return artists;
    }
}
