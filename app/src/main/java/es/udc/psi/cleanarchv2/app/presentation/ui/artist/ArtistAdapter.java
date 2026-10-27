package es.udc.psi.cleanarchv2.app.presentation.ui.artist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import es.udc.psi.cleanarchv2.app.presentation.uimodel.artist.ArtistUiModel;
import es.udc.psi.cleanarchv2.databinding.RowArtistBinding;

public class ArtistAdapter extends Adapter<ArtistAdapter.ArtistHolder> {

    public interface OnArtistClickListener {

        void onArtistClick(ArtistUiModel artist);
    }

    private List<ArtistUiModel> mItems;

    private final OnArtistClickListener mListener;

    public ArtistAdapter(OnArtistClickListener listener) {

        mItems = new ArrayList<>();
        mListener = listener;
    }

    public void setItems(List<ArtistUiModel> items) {

        mItems = items;
        notifyDataSetChanged();
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

        holder.bind(mItems.get(position), mListener);
    }

    static class ArtistHolder extends RecyclerView.ViewHolder {

        private final RowArtistBinding mBinding;

        private ArtistHolder(RowArtistBinding binding) {

            super(binding.getRoot());
            mBinding = binding;
        }

        private void bind(ArtistUiModel artist,
                          OnArtistClickListener listener) {

            mBinding.rowArtistId.setText(artist.getId());
            mBinding.rowArtistName.setText(artist.getName());
            mBinding.getRoot().setOnClickListener(v -> listener.onArtistClick(artist));
        }
    }

}
