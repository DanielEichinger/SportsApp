package com.example.sportsapp.models

interface EventStore {
    fun getAll(): List<Event>
    fun create(event: Event)
    fun update(event: Event)
}