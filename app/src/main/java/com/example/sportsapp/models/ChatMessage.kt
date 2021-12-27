package com.example.sportsapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ChatMessage(var id: Int = 0,
                       var message : String = "",
                       var user: User = User(""),
                       var time: LocalDateTime = LocalDateTime.now()) : Parcelable
