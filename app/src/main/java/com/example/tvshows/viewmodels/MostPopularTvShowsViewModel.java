package com.example.tvshows.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshows.repositories.MostPopularTvShowsRepository;
import com.example.tvshows.responses.TvShowResponse;

public class MostPopularTvShowsViewModel extends ViewModel {

    private MostPopularTvShowsRepository mostPopularTvShowsRepository;

    public MostPopularTvShowsViewModel(){
        mostPopularTvShowsRepository=new MostPopularTvShowsRepository();
    }

    public LiveData<TvShowResponse> getMostPopularTvShows(int page){

        return mostPopularTvShowsRepository.getMostPopularTvShows(page);
    }
}
