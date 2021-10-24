package com.scientisthamsterssofiandjohn.weatherapp.di.module

import android.content.Context
import androidx.room.Room
import com.scientisthamsterssofiandjohn.weatherapp.App
import com.scientisthamsterssofiandjohn.weatherapp.data.local.WeatherDao
import com.scientisthamsterssofiandjohn.weatherapp.data.local.WeatherLocalStorage
import com.scientisthamsterssofiandjohn.weatherapp.data.remote.WeatherApi
import com.scientisthamsterssofiandjohn.weatherapp.domain.repository.WeatherRepository
import com.scientisthamsterssofiandjohn.weatherapp.utils.Constants.Companion.DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherApi: WeatherApi,
        weatherDao: WeatherDao
    ): WeatherRepository {
        return WeatherRepository(weatherApi, weatherDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        WeatherLocalStorage::class.java,
        DATABASE
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideWeatherDao(database: WeatherLocalStorage) = database.weatherDao()
}