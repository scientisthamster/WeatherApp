package com.scientisthamsterssofiandjohn.weatherapp.view.ui.fragments.today

import android.animation.LayoutTransition
import android.app.SearchManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.scientisthamsterssofiandjohn.weatherapp.R
import com.scientisthamsterssofiandjohn.weatherapp.data.Resource
import com.scientisthamsterssofiandjohn.weatherapp.databinding.FragmentTodayBinding
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.WeatherResponse
import com.scientisthamsterssofiandjohn.weatherapp.utils.Constants.Companion.PREF_CITY
import com.scientisthamsterssofiandjohn.weatherapp.view.WeatherActivity
import com.scientisthamsterssofiandjohn.weatherapp.view.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodayFragment : Fragment(R.layout.fragment_today) {

    private lateinit var binding: FragmentTodayBinding

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var searchView: SearchView

    private lateinit var sharedPreferences: SharedPreferences

    private var weatherResponse: WeatherResponse? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTodayBinding.bind(view)
        setHasOptionsMenu(true)

        (activity as WeatherActivity).setSupportActionBar(binding.toolbar)
        (activity as WeatherActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)

        sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.pref_city)
        val city = sharedPreferences.getString(PREF_CITY, defaultValue)

        viewModel.getCurrentWeather(city = city ?: "Kiev")

        viewModel.weatherResponse.observe(viewLifecycleOwner, Observer {
            updateUI(it)
            weatherResponse = it.data
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
                    binding.tvDegree.text = weatherResponse.data.main.temp.toInt().toString()
                    binding.tvWet.text = weatherResponse.data.main.humidity.toString() + "%"
                    binding.tvClouds.text = weatherResponse.data.clouds.all.toString() + "%"
                    binding.tvWindSpeed.text =
                        getString(R.string.speed, weatherResponse.data.wind.speed.toString())
                    binding.tvMaxTemp.text =
                        getString(
                            R.string.celsius,
                            weatherResponse.data.main.temp_max.toInt().toString()
                        )
                    binding.tvMinTemp.text =
                        getString(
                            R.string.celsius,
                            weatherResponse.data.main.temp_min.toInt().toString()
                        )
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val menuItemSearch = menu.findItem(R.id.action_search)
        searchView = menuItemSearch.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        personalizeSearchView()
        initSearchViewListener()
    }

    private fun initSearchViewListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                this.callSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            private fun callSearch(query: String) {
                viewModel.getCurrentWeather(query)
                sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                with(sharedPreferences.edit()) {
                    putString(PREF_CITY, query)
                        .apply()
                }

            }
        })
    }

    private fun personalizeSearchView() {
        val txtSearch =
            searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        txtSearch.hint = getString(R.string.search)
        txtSearch.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        txtSearch.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

        val closeButton = searchView.findViewById<ImageView>(R.id.search_close_btn)
        closeButton.setImageResource(R.drawable.ic_close)

        val searchBar = searchView.findViewById(R.id.search_bar) as LinearLayout
        searchBar.layoutTransition = LayoutTransition()
    }
}