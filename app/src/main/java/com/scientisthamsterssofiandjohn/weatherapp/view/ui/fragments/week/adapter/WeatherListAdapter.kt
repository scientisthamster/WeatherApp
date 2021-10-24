package com.scientisthamsterssofiandjohn.weatherapp.view.ui.fragments.week.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scientisthamsterssofiandjohn.weatherapp.databinding.ItemLayoutBinding
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.ForecastResponse
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.ListItem
import com.scientisthamsterssofiandjohn.weatherapp.view.ui.fragments.week.viewholder.WeekViewHolder

class WeatherListAdapter(private val listener: weatherInteractionListener) :
    RecyclerView.Adapter<WeekViewHolder>() {

    private var weatherList: List<ListItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        return WeekViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.bind(weather, listener)
    }

    override fun getItemCount(): Int = weatherList.size

    fun submitList(submitList: List<ListItem>) {
        this.weatherList = submitList
        notifyDataSetChanged()
    }
}