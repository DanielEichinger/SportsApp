package com.example.sportsapp.models

interface LocationStore {
    fun getAll() : List<Location>
    fun create(location : Location)
}