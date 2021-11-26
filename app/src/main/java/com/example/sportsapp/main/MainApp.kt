package com.example.sportsapp.main

import android.app.Application
import com.example.sportsapp.models.Location
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    val locations = ArrayList<Location>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("SportsApp started")

        locations.add(Location("Swimming pool", "Public swimming pool next to the school", setOf("Swimming", "Table tennis")))
        locations.add(Location("Football field", "Description for Football field\n" +
                "this could also go multiple lines\n" +
                "maybe some additional information:\n" +
                "08:00 - 20:00, Mo - Fr", setOf("Football")))
        locations.add(Location("Public park", "Description for Public park", setOf("Basketball")))
    }
}