package ru.yellowshark.favoritemovies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import ru.yellowshark.favoritemovies.domain.Repository
import ru.yellowshark.favoritemovies.ui.home.HomeViewModel

@Module
@InstallIn(ActivityRetainedComponent::class)
object ViewModelModule {
    @Provides
    fun provideHomeViewModel(repository: Repository): HomeViewModel {
        return HomeViewModel(repository);
    }
}