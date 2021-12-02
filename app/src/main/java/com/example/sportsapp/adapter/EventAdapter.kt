package com.example.sportsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsapp.databinding.CardEventBinding
import com.example.sportsapp.models.Event

class EventAdapter constructor(private var events: List<Event>) :
    RecyclerView.Adapter<EventAdapter.MainHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val event = events[holder.adapterPosition]
        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size

    class MainHolder(private val binding: CardEventBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.eventName.text = event.title
            binding.eventDescription.text = event.description
            binding.eventLocation.text = event.location.name
        }
    }
}