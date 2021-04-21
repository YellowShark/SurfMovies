package ru.yellowshark.favoritemovies.data.network.api

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import ru.yellowshark.favoritemovies.data.network.response.DiscoverResponse
import ru.yellowshark.favoritemovies.data.network.response.GenresResponse
import ru.yellowshark.favoritemovies.data.network.response.SearchResponse

interface MovieApi {
    @GET("/3/discover")
    fun getMovies(): Observable<DiscoverResponse>

    @GET("search/search-movies")
    fun searchMovies(query: String): Single<SearchResponse>

    @GET("genres/get-movie-list")
    fun getGenres(): Single<GenresResponse>
}