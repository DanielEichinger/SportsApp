package com.example.sportsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsapp.databinding.CardChatMessageBinding
import com.example.sportsapp.main.MainApp
import com.example.sportsapp.models.ChatMessage

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
            binding.time.text = chatMessage.time.toString()
        }
    }
}