package com.example.tvshows.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tvshows.network.ApiClient;
import com.example.tvshows.network.ApiService;
import com.example.tvshows.responses.TvShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTvShowsRepository {

    private ApiService apiClient;

    public MostPopularTvShowsRepository(){
        apiClient=ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TvShowResponse> getMostPopularTvShows(int page)
    {
        MutableLiveData<TvShowResponse> data=new MutableLiveData<>();

        apiClient.getMostPopularTvShows(page).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
