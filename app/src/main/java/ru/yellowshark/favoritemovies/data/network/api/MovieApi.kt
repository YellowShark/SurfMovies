package ru.yellowshark.favoritemovies.data.network.api

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.yellowshark.favoritemovies.data.network.response.DiscoverResponse
import ru.yellowshark.favoritemovies.data.network.response.GenresResponse
import ru.yellowshark.favoritemovies.data.network.response.SearchResponse

interface MovieApi {
    @GET("3/discover/movie")
    fun getMovies(): Observable<DiscoverResponse>

    @GET("3/search/movie")
    fun searchMovies(@Query("query") query: String): Observable<SearchResponse>
}