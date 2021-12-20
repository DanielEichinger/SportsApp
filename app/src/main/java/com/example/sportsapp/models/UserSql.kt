package com.example.sportsapp.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserSql : UserModel {
    override fun create(name: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun login(name: String, password: String): Boolean {
        var success = false
        transaction {
            val query: Query = Users.select { Users.name.eq(name) and Users.password.eq(password)}
            success = !query.empty()
        }
        return success
    }
}


object Users : IntIdTable("users") {
    val name = varchar("name", 20)
    val password = varchar("password", 20)
}