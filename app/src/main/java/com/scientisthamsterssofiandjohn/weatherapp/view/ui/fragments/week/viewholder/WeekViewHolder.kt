package com.scientisthamsterssofiandjohn.weatherapp.view.ui.fragments.week.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scientisthamsterssofiandjohn.weatherapp.databinding.ItemLayoutBinding
import com.scientisthamsterssofiandjohn.weatherapp.domain.model.ListItem
import com.scientisthamsterssofiandjohn.weatherapp.utils.Constants.Companion.IMAGE_URL
import java.text.SimpleDateFormat
import java.util.*

class WeekViewHolder(private val binding: ItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        weatherResponse: ListItem?
    ) {
        if (weatherResponse != null) {
            val dayOfWeek = getDateTime(weatherResponse.dt)
            binding.tvDay.text = dayOfWeek
            binding.tvMaxDegree.text = weatherResponse.main.temp_max.toString()
            binding.tvMinDegree.text = weatherResponse.main.temp_min.toString()
            Glide.with(itemView).load(IMAGE_URL+weatherResponse.weather[0].icon+"@2x.png").into(binding.imageView)
        }
    }

    private fun getDateTime(time: Long): String {
        val sdf = SimpleDateFormat("EEEE")
        val dateFormat = Date(time * 1000)
        val weekday = sdf.format(dateFormat)
        return weekday
    }
}