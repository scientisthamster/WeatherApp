package com.scientisthamsterssofiandjohn.weatherapp.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

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

@Entity(tableName = "WeatherLocal")
data class WeatherResult(
    @Embedded
    val clouds: Clouds,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @Embedded
    val main: Main,
    val name: String,
    @Embedded
    val wind: Wind
)

