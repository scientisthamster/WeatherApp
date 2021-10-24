package com.scientisthamsterssofiandjohn.weatherapp.domain.model

data class WeatherResponse(
    val base: String,
    val clouds: Clouds,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind,
    val city: City
)

