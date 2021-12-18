package com.example.sportsapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Event(var title: String = "",
                 var description: String = "",
                 var location: Location = Location(),
                 var date: Date = Date(),
                 var admin: User = User(""),
                 var participants: List<User> = emptyList()) : Parcelable{

}
