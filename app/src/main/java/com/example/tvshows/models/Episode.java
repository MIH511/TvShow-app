package com.example.tvshows.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode {

    @SerializedName("season")
    @Expose
    public Integer season;
    @SerializedName("episode")
    @Expose
    public Integer episode;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("air_date")
    @Expose
    public String airDate;

    public Integer getSeason() {
        return season;
    }

    public Integer getEpisode() {
        return episode;
    }

    public String getName() {
        return name;
    }

    public String getAirDate() {
        return airDate;
    }
}
