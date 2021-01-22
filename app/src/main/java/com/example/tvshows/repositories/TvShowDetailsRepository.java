package com.example.tvshows.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tvshows.network.ApiClient;
import com.example.tvshows.network.ApiService;
import com.example.tvshows.responses.TvShowDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailsRepository {

    ApiService apiClient;

    public TvShowDetailsRepository(){
        apiClient=ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TvShowDetailsResponse> getTvShowDetails(String tvShowId){

        MutableLiveData<TvShowDetailsResponse> data=new MutableLiveData<>();

        apiClient.getTvshowDetails(tvShowId).enqueue(new Callback<TvShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowDetailsResponse> call,@NonNull Response<TvShowDetailsResponse> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowDetailsResponse> call,@NonNull Throwable t) {

                data.setValue(null);
            }
        });
        return data;
    }
}
