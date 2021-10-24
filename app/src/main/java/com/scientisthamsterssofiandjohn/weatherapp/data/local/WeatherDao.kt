package com.scientisthamsterssofiandjohn.weatherapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.WeatherEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherLocal")
    fun getWeather(): LiveData<List<WeatherEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Delete
    suspend fun deleteWeather(weather: WeatherEntity)
}