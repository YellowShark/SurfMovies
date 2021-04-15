package ru.yellowshark.favoritemovies.data.network.response


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("movie_results")
    val movieResults: List<MovieResult>,
    @SerializedName("person_results")
    val personResults: List<Any>,
    @SerializedName("tv_episode_results")
    val tvEpisodeResults: List<Any>,
    @SerializedName("tv_results")
    val tvResults: List<Any>,
    @SerializedName("tv_season_results")
    val tvSeasonResults: List<Any>
)