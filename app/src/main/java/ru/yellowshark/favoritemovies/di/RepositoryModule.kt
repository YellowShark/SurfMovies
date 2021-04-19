package ru.yellowshark.favoritemovies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.yellowshark.favoritemovies.data.RepositoryImpl
import ru.yellowshark.favoritemovies.data.network.api.MovieApi
import ru.yellowshark.favoritemovies.domain.Repository
import ru.yellowshark.favoritemovies.domain.mapper.NetworkMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        api: MovieApi,
        networkMapper: NetworkMapper
    ): Repository {
        return RepositoryImpl(api, networkMapper)
    }

    @Provides
    fun provideNetworkMapper() = NetworkMapper()
}