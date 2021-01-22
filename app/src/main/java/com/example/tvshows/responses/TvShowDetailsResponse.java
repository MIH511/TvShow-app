package com.example.tvshows.responses;

import com.example.tvshows.models.TvShowDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvShowDetailsResponse {

    @SerializedName("tvShow")
    @Expose
    public TvShowDetails tvShow;

    public TvShowDetails getTvShow() {
        return tvShow;
    }
}
