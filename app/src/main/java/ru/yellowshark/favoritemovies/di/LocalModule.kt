package ru.yellowshark.favoritemovies.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.yellowshark.favoritemovies.data.db.MovieDatabase
import ru.yellowshark.favoritemovies.data.db.dao.MovieDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideMovieDb(@ApplicationContext context: Context): MovieDatabase =
        MovieDatabase(context)

    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao =
        movieDatabase.getMovieDao()
}