package es.udc.psi.cleanarchv2.app.presentation.artist;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import java.util.List;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import es.udc.psi.cleanarchv2.R;
import es.udc.psi.cleanarchv2.app.data.artist.ArtistDatasourceImp;
import es.udc.psi.cleanarchv2.app.domain.artist.service.ArtistServiceImp;
import es.udc.psi.cleanarchv2.app.executor.TaskRunner;
import es.udc.psi.cleanarchv2.app.presentation.BaseActivity;
import es.udc.psi.cleanarchv2.app.presentation.artist.adapter.ArtistAdapter;
import es.udc.psi.cleanarchv2.app.presentation.artist.presenter.ArtistsPresenter;
import es.udc.psi.cleanarchv2.app.presentation.artist.presenter.ArtistsPresenterImp;
import es.udc.psi.cleanarchv2.app.presentation.artist.presenter.ArtistsView;
import es.udc.psi.cleanarchv2.app.presentation.artist.model.ArtistUiModel;
import es.udc.psi.cleanarchv2.app.presentation.gig.GigsActivity;
import es.udc.psi.cleanarchv2.databinding.ActivityMainBinding;

public class ArtistsActivity extends BaseActivity<ActivityMainBinding>
        implements ArtistsView, ArtistAdapter.OnArtistClickListener {

    private static final String TAG = ArtistsActivity.class.getSimpleName();

    private ArtistAdapter mAdapter;

    private ArtistsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setUpView();

        mPresenter = new ArtistsPresenterImp(this, new ArtistServiceImp(new ArtistDatasourceImp()), new TaskRunner());
        mPresenter.initFlow();
    }

    @Override
    protected ActivityMainBinding inflateBinding(LayoutInflater inflater) {

        return ActivityMainBinding.inflate(inflater);
    }

    @Override
    protected void onDestroy() {

        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onArtistClick(ArtistUiModel artist) {

        mPresenter.onClickArtist(artist);
    }

    @Override
    public void showLoading() {

        mBinding.mainProgress.setVisibility(View.VISIBLE);
        mBinding.mainEmptyView.setVisibility(View.GONE);
        mBinding.mainList.setVisibility(View.GONE);
    }

    @Override
    public void showArtists(List<ArtistUiModel> artists) {

        mAdapter.setItems(artists);
        mBinding.mainList.scrollToPosition(0);
        mBinding.mainProgress.setVisibility(View.GONE);
        mBinding.mainEmptyView.setVisibility(View.GONE);
        mBinding.mainList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {

        showMessage(R.string.artists_empty_view);
    }

    @Override
    public void showNoResults() {

        showMessage(R.string.artists_no_results);
    }

    @Override
    public void showError() {

        showMessage(R.string.artists_no_results);
        Toast.makeText(this, R.string.error_general, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyQueryError() {

        mBinding.mainSearchLayout.setError(getString(R.string.artists_search_empty));
    }

    @Override
    public void hideQueryError() {

        mBinding.mainSearchLayout.setError(null);
    }

    @Override
    public void hideKeyboard() {

        WindowCompat.getInsetsController(getWindow(), mBinding.mainSearchText).hide(WindowInsetsCompat.Type.ime());
    }

    @Override
    public void navigateToGigs(String artistId,
                               String artistName) {

        startActivity(GigsActivity.newIntent(this, artistId, artistName));
    }

    private void setUpView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.mainList.setLayoutManager(linearLayoutManager);

        mAdapter = new ArtistAdapter(this);
        mBinding.mainList.setAdapter(mAdapter);

        mBinding.mainSearchButton.setOnClickListener(v -> search());
        mBinding.mainSearchText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
                return true;
            }
            return false;
        });
    }

    private void search() {

        Editable text = mBinding.mainSearchText.getText();
        mPresenter.onSearch(text != null ? text.toString() : "");
    }

    private void showMessage(int message) {

        mBinding.mainEmptyView.setText(message);
        mBinding.mainProgress.setVisibility(View.GONE);
        mBinding.mainEmptyView.setVisibility(View.VISIBLE);
        mBinding.mainList.setVisibility(View.GONE);
    }
}
