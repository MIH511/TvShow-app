package com.example.tvshows.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshows.repositories.SearchTvShowRepository;
import com.example.tvshows.responses.TvShowResponse;

public class SearchViewModel extends ViewModel {

    private SearchTvShowRepository searchTvShowRepository;

    public SearchViewModel() {
        this.searchTvShowRepository = new SearchTvShowRepository();
    }

    public LiveData<TvShowResponse> searchTVShow(String quary, int page){
        return searchTvShowRepository.searchTVShow(quary,page);
    }
}
