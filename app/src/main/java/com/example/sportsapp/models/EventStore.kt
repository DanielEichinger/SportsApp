package com.example.sportsapp.models

interface EventStore {
    fun getAll(): List<Event>
    fun create(event: Event)
    fun update(event: Event)
    fun addParticipant(u_id: Int, e_id: Int) : Boolean
}