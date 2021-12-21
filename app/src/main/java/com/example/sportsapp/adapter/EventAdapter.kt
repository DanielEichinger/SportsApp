package com.example.sportsapp.adapter

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsapp.R
import com.example.sportsapp.databinding.CardEventBinding
import com.example.sportsapp.main.MainApp
import com.example.sportsapp.models.Event
import android.content.Context
import com.example.sportsapp.activities.EventListener

class EventAdapter constructor(private var events: List<Event>,
                               private val listener: EventListener) :
    RecyclerView.Adapter<EventAdapter.MainHolder>(){

    lateinit var app : MainApp

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val event = events[holder.adapterPosition]
        holder.bind(event, listener)
    }

    override fun getItemCount(): Int = events.size

    class MainHolder(private val binding: CardEventBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event, listener: EventListener) {
            binding.eventName.text = event.title
            binding.eventDescription.text = event.description
            binding.eventLocation.text = event.location.name
            //binding.eventAdmin.text = binding.root.context.getString(R.string.event_list_created_by) + " " + event.admin?.username
            binding.root.setOnClickListener { listener.onEventClick(event) }
        }
    }
}