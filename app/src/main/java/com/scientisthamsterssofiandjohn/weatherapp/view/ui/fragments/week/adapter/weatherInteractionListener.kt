package com.scientisthamsterssofiandjohn.weatherapp.view.ui.fragments.week.adapter

import com.scientisthamsterssofiandjohn.weatherapp.domain.model.ListItem

interface weatherInteractionListener {
    fun onMovieClick(weatherResponse: ListItem, position: Int)
}