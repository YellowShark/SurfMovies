package ru.yellowshark.favoritemovies.data.db.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.yellowshark.favoritemovies.data.db.entity.MovieEntity
import ru.yellowshark.favoritemovies.data.db.entity.SearchedMovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movieEntities: List<MovieEntity>): Completable

    @Query("SELECT * FROM table_local_movies")
    fun getMovies(): Observable<List<MovieEntity>>

    @Query("SELECT * FROM table_searched_movie WHERE title LIKE :title")
    fun getMoviesByTitle(title: String): Single<List<MovieEntity>>

    @Update
    fun updateMovie(movieEntity: MovieEntity): Completable

    @Update
    fun updateSearchedMovie(movieEntity: SearchedMovieEntity): Completable
}