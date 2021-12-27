package com.example.sportsapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class Event(var id: Int = 0,
                 var title: String = "",
                 var description: String = "",
                 var location: Location = Location(),
                 var dateTime: LocalDateTime = LocalDateTime.now(),
                 var admin: User = User(""),
                 var participants: ArrayList<User> = arrayListOf(),
                 var chatHistory: ArrayList<ChatMessage> = arrayListOf()
): Parcelable