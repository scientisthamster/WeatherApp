package com.scientisthamsterssofiandjohn.weatherapp.domain.repository

import com.scientisthamsterssofiandjohn.weatherapp.data.remote.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi
) {
    suspend fun getCurrentWeather(city: String) = weatherApi.getCurrentWeather(city = city)

    suspend fun getForecastWeather(city: String) = weatherApi.getForecastWeather(city = city)
}