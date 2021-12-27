package com.example.sportsapp.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import timber.log.Timber.i

class EventSqlStore (_location: LocationStore): EventStore {

    val events = ArrayList<Event>()
    val location = _location

    override fun getAll(): List<Event> {

        events.clear()

        transaction {
            val eventResult: Query = EventsTable
                .join(UsersTable, JoinType.INNER, EventsTable.admin, UsersTable.id)
                .selectAll()

            eventResult.forEach {
                events.add(Event(it[EventsTable.id].value,      // ID
                    it[EventsTable.title],                      // Title
                    it[EventsTable.description],                // Description
                    location.getById(it[EventsTable.l_id])!!,   // Location
                    it[EventsTable.time],                       // Time of Event
                    User(it[UsersTable.name])))                 // Admin

                i("${it[EventsTable.id]} ${it[EventsTable.title]} ${it[EventsTable.description]} ${it[EventsTable.l_id]}")
            }

            // Participants list
            val participantsResult: Query = ParticipantsTable
                .join(UsersTable, JoinType.INNER, ParticipantsTable.u_id, UsersTable.id)
                .selectAll()

            participantsResult.forEach { par_it ->
                events.find { par_it[ParticipantsTable.e_id] == it.id}
                    ?.participants?.add(User(par_it[UsersTable.name]))

            }

            // Chat History List
            val chatResult: Query = ChatMessagesTable
                .join(UsersTable, JoinType.INNER, ChatMessagesTable.u_id, UsersTable.id)
                .selectAll()
            chatResult.forEach { chat_it ->
                events.find {chat_it[ChatMessagesTable.e_id] == it.id}
                    ?.chatHistory?.add(ChatMessage(chat_it[ChatMessagesTable.id].value,
                        chat_it[ChatMessagesTable.mesage],
                        User(chat_it[UsersTable.name]),
                        chat_it[ChatMessagesTable.time]))
            }
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
    val time = datetime("time")
}

object UsersTable : IntIdTable("users") {
    val name = varchar("name", 20)
    val password = varchar("password", 20)
}

object ParticipantsTable : Table("participants") {
    val e_id = integer("e_id")
    val u_id = integer("u_id")
}

object ChatMessagesTable : IntIdTable("chat_messages") {
    val mesage = varchar("message", 256)
    val u_id = integer("u_id")
    val e_id = integer("e_id")
    val time = datetime("time")
}