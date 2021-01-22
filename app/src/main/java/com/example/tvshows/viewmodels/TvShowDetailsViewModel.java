package com.example.tvshows.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshows.DataBase.TvShowDataBase;
import com.example.tvshows.models.TvShow;
import com.example.tvshows.repositories.TvShowDetailsRepository;
import com.example.tvshows.responses.TvShowDetailsResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TvShowDetailsViewModel extends AndroidViewModel {

    TvShowDetailsRepository tvShowDetailsRepository;
    private TvShowDataBase tvShowDataBase;

    public TvShowDetailsViewModel(@NonNull Application application){
        super(application);
        tvShowDetailsRepository=new TvShowDetailsRepository();
        tvShowDataBase=TvShowDataBase.getTvShowDataBase(application);
    }

    public LiveData<TvShowDetailsResponse> getTvShowDetails(String tvShowId){
        
        return tvShowDetailsRepository.getTvShowDetails(tvShowId);
    }

    public Completable addToWatchList(TvShow tvShow){

        return tvShowDataBase.tvShowDao().addToWatchList(tvShow);
    }
    public Flowable<TvShow> getTVShowFromWatchlist(String tvShowId){
        return tvShowDataBase.tvShowDao().getTVShowFromWatchlist(tvShowId);
    }
    public Completable removeTVShowFromWatchlist(TvShow tvShow){
        return tvShowDataBase.tvShowDao().removeFromWatchList(tvShow);
    }
}
