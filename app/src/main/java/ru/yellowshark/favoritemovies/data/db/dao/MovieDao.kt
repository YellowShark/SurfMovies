package ru.yellowshark.favoritemovies.data.db.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.yellowshark.favoritemovies.data.db.TABLE_LOCAL_MOVIES
import ru.yellowshark.favoritemovies.data.db.TABLE_SEARCHED_MOVIES
import ru.yellowshark.favoritemovies.data.db.entity.MovieEntity
import ru.yellowshark.favoritemovies.data.db.entity.SearchedMovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movieEntities: List<MovieEntity>): Completable

    @Query("SELECT * FROM $TABLE_LOCAL_MOVIES")
    fun getMovies(): Observable<List<MovieEntity>>

    @Update
    fun updateMovie(movieEntity: MovieEntity): Completable

    @Query("SELECT * FROM $TABLE_SEARCHED_MOVIES WHERE title LIKE :title")
    fun getMoviesByTitle(title: String): Observable<List<SearchedMovieEntity>>

    @Update
    fun updateSearchedMovie(movieEntity: SearchedMovieEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSearchMovies(movieEntities: List<SearchedMovieEntity>): Completable
}