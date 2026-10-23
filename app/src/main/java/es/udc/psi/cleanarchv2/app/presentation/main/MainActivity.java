package es.udc.psi.cleanarchv2.app.presentation.main;

import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
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

    private List<String> getArtists() {

        List<String> artists = new ArrayList<>();

        artists.add("Ben Harper");
        artists.add("Bon Iver");
        artists.add("The National");
        artists.add("Rancid");
        artists.add("The Black Crows");
        artists.add("The Band");

        return artists;
    }
}
