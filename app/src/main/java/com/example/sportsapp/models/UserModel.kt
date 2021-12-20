package com.example.sportsapp.models

interface UserModel {
    fun create(name: String, password: String)
    fun login(name: String, password: String) : Boolean
}