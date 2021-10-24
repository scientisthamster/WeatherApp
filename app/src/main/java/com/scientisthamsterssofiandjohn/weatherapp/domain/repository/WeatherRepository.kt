package com.scientisthamsterssofiandjohn.weatherapp.domain.repository

import com.scientisthamsterssofiandjohn.weatherapp.data.local.WeatherDao
import com.scientisthamsterssofiandjohn.weatherapp.data.remote.WeatherApi
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.WeatherEntity
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) {
    suspend fun getCurrentWeather(city: String) = weatherApi.getCurrentWeather(city = city)

    suspend fun getForecastWeather(city: String) = weatherApi.getForecastWeather(city = city)

    suspend fun insertWeather(weather: WeatherEntity) = weatherDao.insertWeather(weather)

    suspend fun deleteWeather(weather: WeatherEntity) = weatherDao.deleteWeather(weather)

    fun getSavedWeather() = weatherDao.getWeather()
}