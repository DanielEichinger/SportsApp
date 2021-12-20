package com.example.sportsapp.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class LocationSql {

    fun getAll() : ArrayList<Location>{
        val locations : ArrayList<Location> = arrayListOf()
        transaction {

            val query: Query = Locations.selectAll()

            query.forEach {

                locations.add(Location(it[Locations.id].value,
                    it[Locations.name].toString(), it[Locations.description].toString()))

                println(it[Locations.id])
                println(it[Locations.name])
                println(it[Locations.description])
            }
        }
        return locations
    }
}


object Locations : IntIdTable("locations") {
    val name = varchar("name", 20)
    val description = varchar("description", 20)
}