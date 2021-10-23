package com.scientisthamsterssofiandjohn.weatherapp.domain.model

data class ListItem (
    val dt: Long,
    val rain: Rain,
    val dt_txt: String,
    val main: Main,
    val clouds: Clouds,
    val sys: Sys,
    val wind: Wind,
    val weather: List<WeatherItem>
)