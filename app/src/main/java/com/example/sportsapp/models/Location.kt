package com.example.sportsapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(var id: Int = 0,
                    var name: String = "",
                    var description: String = "",
                    var GpsLoc: GpsLocation = GpsLocation(),
                    var sports: MutableSet<String> = mutableSetOf()) : Parcelable

@Parcelize
data class GpsLocation(var lat: Double = 49.0020,
                       var lng: Double = 12.0962,
                       var zoom: Float = 15f) : Parcelable
