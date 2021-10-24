package com.scientisthamsterssofiandjohn.weatherapp.view.ui.fragments.week

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scientisthamsterssofiandjohn.weatherapp.R
import com.scientisthamsterssofiandjohn.weatherapp.data.Resource
import com.scientisthamsterssofiandjohn.weatherapp.databinding.FragmentWeekBinding
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.ForecastResponse
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.ListItem
import com.scientisthamsterssofiandjohn.weatherapp.utils.Constants.Companion.PREF_CITY
import com.scientisthamsterssofiandjohn.weatherapp.view.ui.fragments.week.adapter.WeatherListAdapter
import com.scientisthamsterssofiandjohn.weatherapp.view.ui.fragments.week.adapter.weatherInteractionListener
import com.scientisthamsterssofiandjohn.weatherapp.view.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeekFragment : Fragment(R.layout.fragment_week), weatherInteractionListener {

    private lateinit var binding: FragmentWeekBinding

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var weatherAdapter: WeatherListAdapter

    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeekBinding.bind(view)

        sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.pref_city)
        val city = sharedPreferences.getString(PREF_CITY, defaultValue)

        viewModel.getForecastWeather(city ?: "Kiev")

        viewModel.forecastResponse.observe(viewLifecycleOwner, Observer {
            initUI()
            updateUI(it)
        })
    }

    private fun initUI() {
        weatherAdapter = WeatherListAdapter(this)
        binding.rvWeather.layoutManager = LinearLayoutManager(activity)
        binding.rvWeather.adapter = weatherAdapter
    }

    private fun updateUI(forecastResponse: Resource<ForecastResponse>) {
        when (forecastResponse) {
            is Resource.Error -> {
                hideProgress()
                Log.e("TAG", forecastResponse.message ?: "An error occurred")
            }
            is Resource.Loading -> showProgress()
            is Resource.Success -> {
                hideProgress()
                if (forecastResponse.data != null) {
                    weatherAdapter.submitList(forecastResponse.data.list)
                }
            }
        }
    }

    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onMovieClick(weatherResponse: ListItem, position: Int) {
        findNavController().navigate(R.id.action_weekFragment_to_todayFragment)
    }
}