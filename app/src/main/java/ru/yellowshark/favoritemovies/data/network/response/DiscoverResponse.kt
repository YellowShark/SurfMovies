package ru.yellowshark.favoritemovies.data.network.response


import com.google.gson.annotations.SerializedName

data class DiscoverResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)