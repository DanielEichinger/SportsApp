package com.example.sportsapp.main

import android.app.Application
import android.os.StrictMode
import com.example.sportsapp.models.*
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.exposed.sql.Database
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    //var locations = ArrayList<Location>()
    lateinit var locations: LocationStore
    //val events = ArrayList<Event>()
    lateinit var events: EventStore
    lateinit var user : User
    val userlogin : UserSql = UserSql()
    lateinit var db: Database;

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("SportsApp started")

        locations = LocationSqlStore()
        events = EventSqlStore(locations)
/*
        locations.add(Location(0, "Swimming pool", "Public swimming pool next to the school", setOf("Swimming", "Table tennis")))
        locations.add(Location(1, "Football field", "Description for Football field\n" +
                "this could also go multiple lines\n" +
                "maybe some additional information:\n" +
                "08:00 - 20:00, Mo - Fr", setOf("Football")))
        locations.add(Location(2, "Public park", "Description for Public park", setOf("Basketball")))
*/
        connectToSql()
    }

    private fun connectToSql() {

        // TODO: Implement network activity asynchronously
        // https://stackoverflow.com/questions/66946890/mysql-android-studio-java-sql-sqlnontransientconnectionexception-could-not-cre
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            db = Database.connect("jdbc:mysql://192.168.2.108:3306/sports_app?autoReconnect=true&useSSl=false",
                driver="com.mysql.jdbc.Driver",
                user = "kotlin",
                password = "kotlin")
        } catch (e: Exception) {
            i("Connection to server failed")
        }
    }
}

