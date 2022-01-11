package com.example.sportsapp.models

import com.example.sportsapp.sql.ActivityTable
import com.example.sportsapp.sql.LocationsTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import timber.log.Timber.i

class LocationSqlStore : LocationStore {

    val locations = ArrayList<Location>()

    override fun getAll(): List<Location> {

        // Clear list before adding new entries
        locations.clear()

        transaction {
            val locationResult: Query = LocationsTable.selectAll()

            locationResult.forEach {
                locations.add(Location(it[LocationsTable.id].value,
                    it[LocationsTable.name].toString(),
                    it[LocationsTable.description].toString(),
                    GpsLocation(it[LocationsTable.latitude],
                        it[LocationsTable.longitude],
                        it[LocationsTable.zoom]
                    )
                ))

                i("${it[LocationsTable.id]} ${it[LocationsTable.name]}")
            }

            // Go through all activities and add them to the corresponding location
            val activityResult: Query = ActivityTable.selectAll()
            activityResult.forEach { act_it ->
                locations.find { it.id == act_it[ActivityTable.l_id]}
                    ?.sports?.add(act_it[ActivityTable.activity].toString())
            }
        }
        return locations
    }

    override fun getById(id: Int): Location? {
        return getAll().find { it.id == id }
    }

    override fun create(location: Location) {
        transaction {
            val id = LocationsTable.insertAndGetId {
                it[name] = location.name
                it[description] = location.description
                it[latitude] = location.GpsLoc.lat
                it[longitude] = location.GpsLoc.lng
                it[zoom] = location.GpsLoc.zoom
            }

            location.sports.forEach { act_it ->
                ActivityTable.insert {
                    it[l_id] = id.value
                    it[activity] = act_it
                }
            }
        }
    }
}


