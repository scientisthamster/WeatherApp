package com.scientisthamsterssofiandjohn.weatherapp.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scientisthamsterssofiandjohn.weatherapp.data.Resource
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.ForecastResponse
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.WeatherEntity
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.WeatherResponse
import com.scientisthamsterssofiandjohn.weatherapp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherResponse: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()

    val weatherResponse: LiveData<Resource<WeatherResponse>>
        get() = _weatherResponse

    private val _forecastResponse: MutableLiveData<Resource<ForecastResponse>> =
        MutableLiveData()

    val forecastResponse: LiveData<Resource<ForecastResponse>>
        get() = _forecastResponse

    private val _savedWeather: MutableLiveData<List<WeatherEntity>> = MutableLiveData()

    val savedWeather: LiveData<List<WeatherEntity>>
        get() = _savedWeather

    fun getCurrentWeather(city: String) = viewModelScope.launch {
        _weatherResponse.postValue(Resource.Loading())
        weatherRepository.getCurrentWeather(city = city).let { weatherResponse ->
            if (weatherResponse.isSuccessful) {
                if (weatherResponse.body() != null) {
                    insertWeather(weatherResponse.body()!!)
                }
                _weatherResponse.postValue(Resource.Success(weatherResponse.body()!!))
            } else {
                _weatherResponse.postValue(
                    Resource.Error(
                        weatherResponse.message()
                    )
                )
            }
        }
    }

    fun getForecastWeather(city: String) = viewModelScope.launch {
        _forecastResponse.postValue(Resource.Loading())
        weatherRepository.getForecastWeather(city = city).let { weatherResponse ->
            if (weatherResponse.isSuccessful) {
                _forecastResponse.postValue(Resource.Success(weatherResponse.body()!!))
            } else {
                _forecastResponse.postValue(
                    Resource.Error(
                        weatherResponse.message()
                    )
                )
            }
        }
    }

    private suspend fun insertWeather(weatherResponse: WeatherResponse) {
        val weatherEntity = WeatherEntity()
        weatherEntity.maxTemp = weatherResponse.main.temp_max
        weatherEntity.minTemp = weatherResponse.main.temp_min
        weatherEntity.temp = weatherResponse.main.temp
        weatherEntity.cityName = weatherResponse.name
        weatherEntity.clouds = weatherResponse.clouds.all
        weatherEntity.windSpeed = weatherResponse.wind.speed
        weatherEntity.wet = weatherResponse.main.humidity
        weatherRepository.insertWeather(weatherEntity)
    }

    fun getSavedWeather() = viewModelScope.launch {
        weatherRepository.getSavedWeather().let { weather ->
            _savedWeather.postValue(weather.value)
        }
    }
}