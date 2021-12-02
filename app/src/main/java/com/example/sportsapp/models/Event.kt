package com.example.sportsapp.models

import java.util.*

data class Event(var title: String = "",
                 var description: String = "",
                 var location: Location = Location(),
                 var date: Date = Date(),
                 var admin: User? = null,
                 var participants: List<User>? = null){

}
