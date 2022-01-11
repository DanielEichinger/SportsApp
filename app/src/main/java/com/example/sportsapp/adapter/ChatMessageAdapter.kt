package com.example.sportsapp.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsapp.R
import com.example.sportsapp.databinding.CardChatMessageBinding
import com.example.sportsapp.main.MainApp
import com.example.sportsapp.models.ChatMessage
import com.squareup.picasso.Picasso
import timber.log.Timber.i
import java.time.format.DateTimeFormatter

class ChatMessageAdapter constructor(private var chatHistory: List<ChatMessage>) :
    RecyclerView.Adapter<ChatMessageAdapter.MainHolder>(){

    lateinit var app : MainApp

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardChatMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val chatMessage = chatHistory[holder.adapterPosition]
        holder.bind(chatMessage)
    }

    override fun getItemCount(): Int = chatHistory.size

    class MainHolder(private val binding: CardChatMessageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: ChatMessage) {
            binding.message.text = chatMessage.message
            binding.user.text = chatMessage.user.username
            binding.time.text = chatMessage.time.format(DateTimeFormatter.ofPattern("HH:mm  dd.MM.yyyy"))

            Picasso.get()
                .load(R.drawable.default_avatar_chat)
                .placeholder(R.drawable.default_avatar_chat)
                .into(binding.imageView3)
        }
    }
}