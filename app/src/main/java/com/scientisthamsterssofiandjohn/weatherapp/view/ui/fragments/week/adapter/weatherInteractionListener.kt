package com.scientisthamsterssofiandjohn.weatherapp.view.ui.fragments.week.adapter

import com.scientisthamsterssofiandjohn.weatherapp.domain.model.ForecastResponse

interface weatherInteractionListener {
    fun onMovieClick(weatherResponse: ForecastResponse, position: Int)
}