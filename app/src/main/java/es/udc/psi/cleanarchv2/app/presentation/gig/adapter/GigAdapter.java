package es.udc.psi.cleanarchv2.app.presentation.gig.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import es.udc.psi.cleanarchv2.app.domain.City;
import es.udc.psi.cleanarchv2.app.domain.SetList;
import es.udc.psi.cleanarchv2.app.domain.Venue;
import es.udc.psi.cleanarchv2.databinding.RowGigBinding;

public class GigAdapter extends Adapter<GigAdapter.GigHolder> {

    private List<SetList> mItems;

    public GigAdapter() {

        mItems = new ArrayList<>();
    }

    public void setItems(List<SetList> items) {

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

        private void bind(SetList gig) {

            mBinding.rowGigDate.setText(gig.getEventDate());

            Venue venue = gig.getVenue();
            String venueName = "";
            String place = "";
            if (venue != null) {

                venueName = venue.getName();
                City city = venue.getCity();
                if (city != null) {
                    place = city.getName();
                    if (city.getCountry() != null) {
                        place = place + ", " + city.getCountry().getName();
                    }
                }
            }
            mBinding.rowGigVenue.setText(venueName);
            mBinding.rowGigPlace.setText(place);

            if (gig.getTour() != null) {
                mBinding.rowGigTour.setText(gig.getTour().getName());
                mBinding.rowGigTour.setVisibility(View.VISIBLE);
            } else {
                mBinding.rowGigTour.setVisibility(View.GONE);
            }
        }
    }

}
