package com.example.sportsapp.main

import android.app.Application
import com.example.sportsapp.models.Event
import com.example.sportsapp.models.Location
import com.example.sportsapp.models.User
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    val locations = ArrayList<Location>()
    val events = ArrayList<Event>()
    lateinit var user : User

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("SportsApp started")

        locations.add(Location(0, "Swimming pool", "Public swimming pool next to the school", setOf("Swimming", "Table tennis")))
        locations.add(Location(1, "Football field", "Description for Football field\n" +
                "this could also go multiple lines\n" +
                "maybe some additional information:\n" +
                "08:00 - 20:00, Mo - Fr", setOf("Football")))
        locations.add(Location(2, "Public park", "Description for Public park", setOf("Basketball")))
    }
}