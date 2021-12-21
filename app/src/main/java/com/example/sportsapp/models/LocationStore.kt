package com.example.sportsapp.models

interface LocationStore {
    fun getAll() : List<Location>
    fun getById(id: Int): Location?
    fun create(location : Location)
}