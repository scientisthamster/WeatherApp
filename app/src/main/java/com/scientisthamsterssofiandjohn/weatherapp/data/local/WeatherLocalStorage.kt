package com.scientisthamsterssofiandjohn.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 2, exportSchema = false)
abstract class WeatherLocalStorage : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}