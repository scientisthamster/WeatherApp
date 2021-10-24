package com.scientisthamsterssofiandjohn.weatherapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WeatherLocal")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,
    var temp: Double? = null,
    var cityName: String? = null,
    var maxTemp: Double? = null,
    var minTemp: Double? = null,
    var clouds: Int? = null,
    var windSpeed: Double? = null,
    var wet: Int? = null
)