package com.example.sportsapp.models

interface ChatMessageStore {
    fun getAll(e_id: Int) : ArrayList<ChatMessage>
    fun create(ev_id: Int, chatMessage: ChatMessage)
}