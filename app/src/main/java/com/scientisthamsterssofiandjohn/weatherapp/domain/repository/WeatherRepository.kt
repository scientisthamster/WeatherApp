package com.scientisthamsterssofiandjohn.weatherapp.domain.repository

import com.scientisthamsterssofiandjohn.weatherapp.data.local.WeatherDao
import com.scientisthamsterssofiandjohn.weatherapp.data.remote.WeatherApi
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.WeatherResult
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) {
    suspend fun getCurrentWeather(city: String) = weatherApi.getCurrentWeather(city = city)

    suspend fun getForecastWeather(city: String) = weatherApi.getForecastWeather(city = city)

    suspend fun insertWeather(weatherResult: WeatherResult) = weatherDao.insertWeather(weatherResult)

    suspend fun deleteWeather(weatherResult: WeatherResult) = weatherDao.deleteWeather(weatherResult)

    fun getSavedWeather() = weatherDao.getWeather()
}