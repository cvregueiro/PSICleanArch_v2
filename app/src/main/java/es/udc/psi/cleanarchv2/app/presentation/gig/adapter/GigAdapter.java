package es.udc.psi.cleanarchv2.app.presentation.gig.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import es.udc.psi.cleanarchv2.app.presentation.gig.model.GigUiModel;
import es.udc.psi.cleanarchv2.databinding.RowGigBinding;

public class GigAdapter extends Adapter<GigAdapter.GigHolder> {

    private List<GigUiModel> mItems;

    public GigAdapter() {

        mItems = new ArrayList<>();
    }

    public void setItems(List<GigUiModel> items) {

        mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GigAdapter.GigHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                   int viewType) {

        RowGigBinding binding = RowGigBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GigHolder(binding);
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull GigAdapter.GigHolder holder,
                                 int position) {

        holder.bind(mItems.get(position));
    }

    static class GigHolder extends RecyclerView.ViewHolder {

        private final RowGigBinding mBinding;

        private GigHolder(RowGigBinding binding) {

            super(binding.getRoot());
            mBinding = binding;
        }

        private void bind(GigUiModel gig) {

            mBinding.rowGigDate.setText(gig.getDate());
            mBinding.rowGigVenue.setText(gig.getVenue());
            mBinding.rowGigPlace.setText(gig.getPlace());
            mBinding.rowGigTour.setText(gig.getTour());
            mBinding.rowGigTour.setVisibility(gig.hasTour() ? View.VISIBLE : View.GONE);
        }
    }

}
