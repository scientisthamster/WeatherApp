package com.scientisthamsterssofiandjohn.weatherapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.WeatherResult

@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherLocal")
    fun getWeather(): LiveData<List<WeatherResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherResult: WeatherResult)

    @Delete
    suspend fun deleteWeather(weatherResult: WeatherResult)
}