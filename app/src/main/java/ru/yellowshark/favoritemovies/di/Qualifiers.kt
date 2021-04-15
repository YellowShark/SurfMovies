package ru.yellowshark.favoritemovies.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ConnectivityInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKeyInterceptor