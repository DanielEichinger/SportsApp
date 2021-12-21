package com.example.sportsapp.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import timber.log.Timber.i

class EventSqlStore (_location: LocationStore): EventStore {

    val events = ArrayList<Event>()
    val location = _location

    override fun getAll(): List<Event> {

        events.clear()

        transaction {
            val eventResult: Query = EventsTable.selectAll()

            eventResult.forEach {
                events.add(Event(it[EventsTable.id].value,
                    it[EventsTable.title].toString(),
                    it[EventsTable.description].toString(),
                    location.getById(it[EventsTable.l_id])!!))
                i("${it[EventsTable.id]} ${it[EventsTable.title]} ${it[EventsTable.description]} ${it[EventsTable.l_id]}")
            }

            // Go through all activities and add them to the corresponding location
//            val activityResult: Query = ActivityTable.selectAll()
//            activityResult.forEach { act_it ->
//                locations.find { it.id == act_it[ActivityTable.l_id]}
//                    ?.sports?.add(act_it[ActivityTable.activity].toString())
//            }
        }
        return events
    }

    override fun create(event: Event) {
        TODO("Not yet implemented")
    }
}


object EventsTable : IntIdTable("events") {
    val title = varchar("title", 20)
    val description = varchar("description", 256)
    val l_id = integer("l_id")
    val admin = integer("admin")
}
