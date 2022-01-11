package com.example.sportsapp.models

import com.example.sportsapp.sql.ChatMessagesTable
import com.example.sportsapp.sql.EventsTable
import com.example.sportsapp.sql.ParticipantsTable
import com.example.sportsapp.sql.UsersTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.datetime
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
                    User(it[UsersTable.id].value, it[UsersTable.name])))           // Admin

                i("${it[EventsTable.id]} ${it[EventsTable.title]} ${it[EventsTable.description]} ${it[EventsTable.l_id]}")
            }

            // Participants list
            val participantsResult: Query = ParticipantsTable
                .join(UsersTable, JoinType.INNER, ParticipantsTable.u_id, UsersTable.id)
                .selectAll()

            participantsResult.forEach { par_it ->
                events.find { par_it[ParticipantsTable.e_id] == it.id}
                    ?.participants?.add(User(0, par_it[UsersTable.name]))

            }

            // Chat History List
            val chatResult: Query = ChatMessagesTable
                .join(UsersTable, JoinType.INNER, ChatMessagesTable.u_id, UsersTable.id)
                .selectAll()
            chatResult.forEach { chat_it ->
                events.find {chat_it[ChatMessagesTable.e_id] == it.id}
                    ?.chatHistory?.add(ChatMessage(chat_it[ChatMessagesTable.id].value,
                        chat_it[ChatMessagesTable.mesage],
                        User(0, chat_it[UsersTable.name]),
                        chat_it[ChatMessagesTable.time]))
            }
        }
        return events
    }

    override fun create(event: Event) {
        transaction {
            EventsTable.insert {
                it[title] = event.title
                it[description] = event.description
                it[l_id] = event.location.id
                it[admin] = event.admin.id
                it[time] = event.dateTime
            }
        }
    }

    override fun update(event: Event) {
        transaction {
            EventsTable.update({ EventsTable.id eq event.id }) {
                it[title] = event.title
                it[description] = event.description
                it[l_id] = event.location.id
                it[time] = event.dateTime
            }
        }
    }

    override fun addParticipant(u_id: Int, e_id: Int) : Boolean{

        var joined = true // Flag to indicate if user was added to the event or not

        transaction {
            val search: Query = ParticipantsTable.select {
                ParticipantsTable.u_id.eq(u_id).and(ParticipantsTable.e_id.eq(e_id))
            }

            if (search.empty()) { // User has not joined the event -> join event
                ParticipantsTable.insert {
                    it[ParticipantsTable.u_id] = u_id
                    it[ParticipantsTable.e_id] = e_id
                }
                joined = true

            } else { // User is already participant of the event -> remove user from event
                ParticipantsTable.deleteWhere {
                    ParticipantsTable.u_id.eq(u_id).and(ParticipantsTable.e_id.eq(e_id))
                }
                joined = false
            }
        }
        return joined
    }
}
