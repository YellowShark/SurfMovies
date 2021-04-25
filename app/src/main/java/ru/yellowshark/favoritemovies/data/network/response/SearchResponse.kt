package ru.yellowshark.favoritemovies.data.network.response


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("results")
    val results: List<MovieResult>
)