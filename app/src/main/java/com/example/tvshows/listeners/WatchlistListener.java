package com.example.tvshows.listeners;

import com.example.tvshows.models.TvShow;

public interface WatchlistListener {

    void onTVShowClicked(TvShow tvShow);
    void removeTvShowFormatwatchlist(TvShow tvShow,int position);
}
