package com.example.tvshows.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshows.R;
import com.example.tvshows.databinding.ItemContainerTvShowBinding;
import com.example.tvshows.listeners.TvShowslistener;
import com.example.tvshows.models.TvShow;

import java.util.List;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowViewHolder>{

    List<TvShow> tvShows;
    LayoutInflater layoutInflater;
    TvShowslistener tvShowslistener;

    public TvShowsAdapter(List<TvShow> tvShows, TvShowslistener tvShowslistener) {
        this.tvShows = tvShows;
        this.tvShowslistener= tvShowslistener;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater=LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvShowBinding tvShowBinding= DataBindingUtil.inflate(layoutInflater, R.layout.item_container_tv_show,parent,false);
        return new TvShowViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {

        holder.bindTvShow(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerTvShowBinding itemContainerTvShowBinding;

        public TvShowViewHolder(ItemContainerTvShowBinding itemContainerTvShowBinding) {
            super(itemContainerTvShowBinding.getRoot());
            this.itemContainerTvShowBinding=itemContainerTvShowBinding;

        }

        public void bindTvShow(TvShow tvShow){

            itemContainerTvShowBinding.setTvShow(tvShow);
            itemContainerTvShowBinding.executePendingBindings();
            itemContainerTvShowBinding.getRoot().setOnClickListener(view -> tvShowslistener.onTvShowClicked(tvShow));
        }
    }
}
