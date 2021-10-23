package com.scientisthamsterssofiandjohn.weatherapp.view.ui.fragments.today

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.scientisthamsterssofiandjohn.weatherapp.R
import com.scientisthamsterssofiandjohn.weatherapp.data.Resource
import com.scientisthamsterssofiandjohn.weatherapp.databinding.ActivityWeatherBinding
import com.scientisthamsterssofiandjohn.weatherapp.databinding.FragmentTodayBinding
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.WeatherResponse
import com.scientisthamsterssofiandjohn.weatherapp.view.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@AndroidEntryPoint
class TodayFragment : Fragment(R.layout.fragment_today) {

    private lateinit var binding: FragmentTodayBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTodayBinding.bind(view)

        viewModel.getCurrentWeather("Kiev")

        viewModel.weatherResponse.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })
    }

    private fun updateUI(weatherResponse: Resource<WeatherResponse>) {
        when (weatherResponse) {
            is Resource.Error -> {
                hideProgress()
                Log.e("tag", weatherResponse.message ?: "An error occurred")
            }
            is Resource.Loading -> showProgress()
            is Resource.Success -> {
                hideProgress()
                if (weatherResponse.data != null) {
                    binding.tvCityName.text = weatherResponse.data.name
                    binding.tvDegree.text = weatherResponse.data.main.temp.toString()
                    binding.tvWet.text = weatherResponse.data.main.humidity.toString() + "%"
                    binding.tvClouds.text = weatherResponse.data.clouds.all.toString() + "%"
                    binding.tvWindSpeed.text =
                        getString(R.string.speed, weatherResponse.data.wind.speed.toString())
                    binding.tvMaxTemp.text =
                        getString(R.string.celsius, weatherResponse.data.main.temp_max.toString())
                    binding.tvMinTemp.text =
                        getString(R.string.celsius, weatherResponse.data.main.temp_min.toString())
                    binding.tvToolbarTitle.text = weatherResponse.data.name
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
}