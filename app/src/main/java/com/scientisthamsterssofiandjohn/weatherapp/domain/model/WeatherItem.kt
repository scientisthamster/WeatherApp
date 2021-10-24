package com.scientisthamsterssofiandjohn.weatherapp.domain.model

data class WeatherItem(
    val icon: String,
    val description: String,
    val main: String,
    val id: Int
)
