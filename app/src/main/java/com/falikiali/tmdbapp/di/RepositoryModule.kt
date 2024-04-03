package com.falikiali.tmdbapp.di

import com.falikiali.tmdbapp.data.ImplMediaRepository
import com.falikiali.tmdbapp.data.ImplPreferencesRepository
import com.falikiali.tmdbapp.domain.repository.MediaRepository
import com.falikiali.tmdbapp.domain.repository.PreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providePreferencesRepository(implPreferencesRepository: ImplPreferencesRepository): PreferencesRepository

    @Binds
    abstract fun provideMediaRepository(implMediaRepository: ImplMediaRepository): MediaRepository

}