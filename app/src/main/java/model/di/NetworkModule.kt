package model.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import model.data.remote.RetrofitInstance
import model.data.remote.api.TripsApi
import model.repository.TripsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApi(): TripsApi =
        RetrofitInstance.api

    @Provides
    @Singleton
    fun provideRepo(api: TripsApi): TripsRepository =
        TripsRepository(api)
}