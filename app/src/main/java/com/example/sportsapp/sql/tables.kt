package com.example.sportsapp.sql

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime


object ActivityTable : Table("activities") {
    val l_id = integer("l_id")
    val activity = varchar("activity", 20)
}

object ChatMessagesTable : IntIdTable("chat_messages") {
    val mesage = varchar("message", 256)
    val e_id = integer("e_id")
    val u_id = integer("u_id")
    val time = datetime("time")
}

object EventsTable : IntIdTable("events") {
    val title = varchar("title", 20)
    val description = varchar("description", 256)
    val l_id = integer("l_id")
    val admin = integer("admin")
    val time = datetime("time")
}

object LocationsTable : IntIdTable("locations") {
    val name = varchar("name", 20)
    val description = varchar("description", 256)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val zoom = float("zoom")
}

object ParticipantsTable : Table("participants") {
    val e_id = integer("e_id")
    val u_id = integer("u_id")
}

object UsersTable : IntIdTable("users") {
    val name = varchar("name", 20)
    val password = varchar("password", 20)
}
