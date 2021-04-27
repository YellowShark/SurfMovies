package ru.yellowshark.favoritemovies.domain

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import ru.yellowshark.favoritemovies.data.network.response.DiscoverResponse
import ru.yellowshark.favoritemovies.data.network.response.Genre
import ru.yellowshark.favoritemovies.domain.model.Movie

interface Repository {
    fun getMovies(): Observable<List<Movie>>

    fun searchMovies(query: String): Single<List<Movie>>
}