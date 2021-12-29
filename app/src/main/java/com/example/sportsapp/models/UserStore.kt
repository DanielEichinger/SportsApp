package com.example.sportsapp.models

interface UserStore {
    fun create(name: String, password: String) : Boolean
    fun login(name: String, password: String) : Boolean
    fun getUserId(): Int
}