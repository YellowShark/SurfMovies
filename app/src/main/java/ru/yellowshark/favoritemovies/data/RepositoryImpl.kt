package ru.yellowshark.favoritemovies.data

import io.reactivex.Observable
import io.reactivex.Single
import ru.yellowshark.favoritemovies.data.db.dao.MovieDao
import ru.yellowshark.favoritemovies.data.network.api.MovieApi
import ru.yellowshark.favoritemovies.data.network.response.Genre
import ru.yellowshark.favoritemovies.domain.Repository
import ru.yellowshark.favoritemovies.domain.mapper.NetworkMapper
import ru.yellowshark.favoritemovies.domain.model.Movie
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val dao: MovieDao,
    private val networkMapper: NetworkMapper
) : Repository {

    override fun getMovies(): Observable<List<Movie>> {
        return api.getMovies()
            .map { response -> response.results.map { networkMapper.toDomain(it) }
        }
    }

    override fun searchMovies(query: String): Single<List<Movie>> {
        return api.searchMovies(query)
            .map { response -> response.results.map { networkMapper.toDomain(it) } }
    }
}