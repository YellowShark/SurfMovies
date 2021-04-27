package ru.yellowshark.favoritemovies.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.yellowshark.favoritemovies.data.db.dao.MovieDao
import ru.yellowshark.favoritemovies.data.db.entity.MovieEntity
import ru.yellowshark.favoritemovies.data.db.entity.SearchedMovieEntity

const val DB_NAME = "movies.db"

@Database(entities = [MovieEntity::class, SearchedMovieEntity::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun getMovieDao(): MovieDao
}