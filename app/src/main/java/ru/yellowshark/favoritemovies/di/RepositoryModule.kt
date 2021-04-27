package ru.yellowshark.favoritemovies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.yellowshark.favoritemovies.data.RepositoryImpl
import ru.yellowshark.favoritemovies.data.db.dao.MovieDao
import ru.yellowshark.favoritemovies.data.network.api.MovieApi
import ru.yellowshark.favoritemovies.domain.Repository
import ru.yellowshark.favoritemovies.domain.mapper.LocalMapper
import ru.yellowshark.favoritemovies.domain.mapper.NetworkMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        api: MovieApi,
        dao: MovieDao,
        networkMapper: NetworkMapper,
        localMapper: LocalMapper
    ): Repository {
        return RepositoryImpl(api, dao, networkMapper)
    }

    @Provides
    fun provideNetworkMapper() = NetworkMapper()

    @Provides
    fun provideLocalMapper() = LocalMapper()
}