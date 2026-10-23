package es.udc.psi.cleanarchv2.app.presentation.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import es.udc.psi.cleanarchv2.app.domain.Artist;
import es.udc.psi.cleanarchv2.databinding.RowArtistBinding;

public class ArtistAdapter extends Adapter<ArtistAdapter.ArtistHolder> {

    private List<Artist> mItems;

    public ArtistAdapter(List<Artist> items) {

        mItems = items;
    }

    @NonNull
    @Override
    public ArtistAdapter.ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                         int viewType) {

        RowArtistBinding binding = RowArtistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArtistHolder(binding);
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistAdapter.ArtistHolder holder,
                                 int position) {

        holder.bind(mItems.get(position));
    }

    static class ArtistHolder extends RecyclerView.ViewHolder {

        private final RowArtistBinding mBinding;

        private ArtistHolder(RowArtistBinding binding) {

            super(binding.getRoot());
            mBinding = binding;
        }

        private void bind(Artist artist) {

            mBinding.rowArtistId.setText(String.valueOf(artist.getId()));
            mBinding.rowArtistName.setText(artist.getName());
        }
    }

}
