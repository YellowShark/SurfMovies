package ru.yellowshark.favoritemovies.data

import android.annotation.SuppressLint
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import ru.yellowshark.favoritemovies.data.db.dao.MovieDao
import ru.yellowshark.favoritemovies.data.network.api.MovieApi
import ru.yellowshark.favoritemovies.domain.Repository
import ru.yellowshark.favoritemovies.domain.mapper.LocalMapper
import ru.yellowshark.favoritemovies.domain.mapper.LocalSearchMapper
import ru.yellowshark.favoritemovies.domain.mapper.NetworkMapper
import ru.yellowshark.favoritemovies.domain.model.Movie
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val dao: MovieDao,
    private val networkMapper: NetworkMapper,
    private val localMapper: LocalMapper,
    private val localSearchMapper: LocalSearchMapper
) : Repository {

    override fun getMovies(): Observable<List<Movie>> {
        val localObservable =
            dao.getMovies()
                .map { it.map { movieEntity -> localMapper.toDomain(movieEntity) } }
                .subscribeOn(Schedulers.computation())

        val remoteObservable = api.getMovies()
            .map { response ->
                saveMoviesInDb(response.results.map { movieResult ->
                    networkMapper.toDomain(movieResult)
                })
                response.results.map { networkMapper.toDomain(it) }
            }.subscribeOn(Schedulers.io())

        return Observable.zip(
            localObservable,
            remoteObservable,
            BiFunction { localMovies, remoteMovies ->
                return@BiFunction if (localMovies.isEmpty()) remoteMovies
                else localMovies
            })
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun saveMoviesInDb(list: List<Movie>) {
        dao.insertMovies(list.map { localMapper.fromDomain(it) })
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    override fun searchMovies(query: String): Observable<List<Movie>> {
        val localSearch = dao.getMoviesByTitle("%$query%")
            .map { it.map { movieEntity -> localSearchMapper.toDomain(movieEntity) } }
            .subscribeOn(Schedulers.computation())

        val remoteSearch = api.searchMovies(query)
            .map {
                saveSearchMovies(it.results.map { movieResult ->
                    networkMapper.toDomain(movieResult)
                })
                it.results.map { dto ->
                    networkMapper.toDomain(dto)
                }
            }
            .subscribeOn(Schedulers.io())

        return localSearch.zipWith(
            remoteSearch,
            BiFunction { localResult, remoteResult ->
                return@BiFunction if (localResult.isEmpty()) remoteResult
                else localResult
            })
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun saveSearchMovies(list: List<Movie>) {
        dao.insertSearchMovies(list.map { localSearchMapper.fromDomain(it) })
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    override fun updateMovie(movie: Movie): Completable {
        val firstTableCompletable = dao.updateMovie(localMapper.fromDomain(movie))
            .subscribeOn(Schedulers.io())

        val secondTableCompletable = dao.updateSearchedMovie(localSearchMapper.fromDomain(movie))
            .subscribeOn(Schedulers.io())

        return firstTableCompletable.ambWith(secondTableCompletable)
            .observeOn(AndroidSchedulers.mainThread())
    }
}