package com.scientisthamsterssofiandjohn.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.WeatherResult

@Database(entities = [WeatherResult::class], version = 1, exportSchema = false)
abstract class WeatherLocalStorage : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}