package com.example.sportsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsapp.activities.LocationListener
import com.example.sportsapp.databinding.CardLocationBinding
import com.example.sportsapp.models.Location


class LocationAdapter constructor(private var locations: List<Location>,
                                  private val listener: LocationListener) :
    RecyclerView.Adapter<LocationAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val sportLocation = locations[holder.adapterPosition]
        holder.bind(sportLocation, listener)
    }

    override fun getItemCount(): Int = locations.size

    class MainHolder(private val binding: CardLocationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(location: Location, listener: LocationListener) {
            binding.locationName.text = location.name
            binding.locationDescription.text = location.description
            var temp_sports : String = ""
            location.sports.forEach { temp_sports = temp_sports.plus(it).plus(", ") }

            // Remove trailing ", "
            temp_sports = temp_sports.dropLast(2)
            binding.locationSports.text = temp_sports

            binding.root.setOnClickListener { listener.onLocationClick(location) }
        }
    }
}