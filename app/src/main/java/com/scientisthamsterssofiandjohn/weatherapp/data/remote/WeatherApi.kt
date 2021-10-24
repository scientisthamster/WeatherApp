package com.scientisthamsterssofiandjohn.weatherapp.data.remote

import com.scientisthamsterssofiandjohn.weatherapp.domain.model.ForecastResponse
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.WeatherResponse
import com.scientisthamsterssofiandjohn.weatherapp.utils.Constants.Companion.API_KEY
import com.scientisthamsterssofiandjohn.weatherapp.utils.Constants.Companion.METRIC
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("units") units: String = METRIC
    ): Response<WeatherResponse>

    @GET("forecast")
    suspend fun getForecastWeather(
        @Query("q") city: String,
        @Query("units") units: String = METRIC
    ) : Response<ForecastResponse>
}