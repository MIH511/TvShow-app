package com.example.tvshows.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tvshows.DataBase.TvShowDataBase;
import com.example.tvshows.models.TvShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchlistViewModel extends AndroidViewModel {

    private TvShowDataBase tvShowDataBase;
    public WatchlistViewModel(@NonNull Application application) {
        super(application);
        tvShowDataBase=TvShowDataBase.getTvShowDataBase(application);
    }

    public Flowable<List<TvShow>> loadWatchlist(){
        return tvShowDataBase.tvShowDao().getWatchList();
    }

    public Completable removeTVshowFormatWatchlist(TvShow tvShow){
        return tvShowDataBase.tvShowDao().removeFromWatchList(tvShow);
    }
}
