package com.example.sportsapp.models

import java.util.*

data class Event(var title: String = "",
                 var description: String = "",
                 var location: Location = Location(),
                 var date: Date = Date(),
                 var admin: User = User(""),
                 var participants: List<User> = emptyList()){

}
