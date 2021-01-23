package com.example.tvshows.network;

import com.example.tvshows.responses.TvShowDetailsResponse;
import com.example.tvshows.responses.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TvShowResponse> getMostPopularTvShows (@Query("page") int page);

    @GET("show-details")
    Call<TvShowDetailsResponse> getTvshowDetails(@Query("q") String tvShowId);

    @GET("search")
    Call<TvShowResponse> searchTVShow(@Query("q") String quary, @Query("page") int page);
}
