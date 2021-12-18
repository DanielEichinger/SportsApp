package com.example.sportsapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(var id: Int = 0,
                    var name: String = "",
                    var description: String = "",
                    var sports: Set<String> = emptySet()) : Parcelable


var lastId = 3

internal fun getId(): Int {
    return lastId++
}