package com.example.tvshows.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowDetails {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("permalink")
    @Expose
    public String permalink;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("description_source")
    @Expose
    public String descriptionSource;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("end_date")
    @Expose
    public Object endDate;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("runtime")
    @Expose
    public Integer runtime;
    @SerializedName("network")
    @Expose
    public String network;
    @SerializedName("youtube_link")
    @Expose
    public Object youtubeLink;
    @SerializedName("image_path")
    @Expose
    public String imagePath;
    @SerializedName("image_thumbnail_path")
    @Expose
    public String imageThumbnailPath;
    @SerializedName("rating")
    @Expose
    public String rating;
    @SerializedName("rating_count")
    @Expose
    public String ratingCount;
    @SerializedName("countdown")
    @Expose
    public Object countdown;
    @SerializedName("genres")
    @Expose
    public List<String> genres = null;
    @SerializedName("pictures")
    @Expose
    public List<String> pictures = null;
    @SerializedName("episodes")
    @Expose
    public List<Episode> episodes = null;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionSource() {
        return descriptionSource;
    }

    public String getStartDate() {
        return startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public String getCountry() {
        return country;
    }

    public String getStatus() {
        return status;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String getNetwork() {
        return network;
    }

    public Object getYoutubeLink() {
        return youtubeLink;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getImageThumbnailPath() {
        return imageThumbnailPath;
    }

    public String getRating() {
        return rating;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public Object getCountdown() {
        return countdown;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }
}
