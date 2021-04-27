package ru.yellowshark.favoritemovies.data.db.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import ru.yellowshark.favoritemovies.data.db.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movieEntity: MovieEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movieEntities: List<MovieEntity>): Completable

    @Query("SELECT * FROM table_local_movies")
    fun getMovies(): Single<List<MovieEntity>>

    @Update
    fun updateMovie(movieEntity: MovieEntity): Completable
}