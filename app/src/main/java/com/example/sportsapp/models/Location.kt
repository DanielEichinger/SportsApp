package com.example.sportsapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(var id: Int = 0,
                    var name: String = "",
                    var description: String = "",
                    var sports: MutableSet<String> = mutableSetOf()) : Parcelable
