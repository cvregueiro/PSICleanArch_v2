package es.udc.psi.cleanarchv2.app.presentation.ui.gig;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import es.udc.psi.cleanarchv2.R;
import es.udc.psi.cleanarchv2.app.GlobalApplication;
import es.udc.psi.cleanarchv2.app.di.AppContainer;
import es.udc.psi.cleanarchv2.app.presentation.presenter.gig.GigsPresenter;
import es.udc.psi.cleanarchv2.app.presentation.presenter.gig.GigsPresenterImp;
import es.udc.psi.cleanarchv2.app.presentation.presenter.gig.GigsView;
import es.udc.psi.cleanarchv2.app.presentation.ui.BaseActivity;
import es.udc.psi.cleanarchv2.app.presentation.uimodel.gig.GigUiModel;
import es.udc.psi.cleanarchv2.databinding.ActivityGigsBinding;

public class GigsActivity extends BaseActivity<ActivityGigsBinding> implements GigsView {

    private static final String EXTRA_ARTIST_ID = "extra_artist_id";
    private static final String EXTRA_ARTIST_NAME = "extra_artist_name";

    private GigAdapter mAdapter;

    private GigsPresenter mPresenter;

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

        AppContainer container = ((GlobalApplication) getApplication()).getContainer();
        mPresenter = new GigsPresenterImp(this, container.getArtistGigsUseCase(), container.taskRunner());
        mPresenter.initFlow(getIntent().getStringExtra(EXTRA_ARTIST_ID));
    }

    @Override
    protected ActivityGigsBinding inflateBinding(LayoutInflater inflater) {

        return ActivityGigsBinding.inflate(inflater);
    }

    @Override
    protected void onDestroy() {

        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {

        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    @Override
    public void showLoading() {

        mBinding.gigsProgress.setVisibility(View.VISIBLE);
        mBinding.gigsEmptyView.setVisibility(View.GONE);
        mBinding.gigsList.setVisibility(View.GONE);
    }

    @Override
    public void showGigs(List<GigUiModel> gigs) {

        mAdapter.setItems(gigs);
        mBinding.gigsProgress.setVisibility(View.GONE);
        mBinding.gigsEmptyView.setVisibility(View.GONE);
        mBinding.gigsList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {

        showMessage(R.string.gigs_no_results);
    }

    @Override
    public void showMissingApiKey() {

        showMessage(R.string.gigs_no_api_key);
    }

    @Override
    public void showError() {

        showMessage(R.string.gigs_no_results);
        Toast.makeText(this, R.string.error_general, Toast.LENGTH_SHORT).show();
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

    private void showMessage(@StringRes int message) {

        mBinding.gigsEmptyView.setText(message);
        mBinding.gigsProgress.setVisibility(View.GONE);
        mBinding.gigsEmptyView.setVisibility(View.VISIBLE);
        mBinding.gigsList.setVisibility(View.GONE);
    }
}
