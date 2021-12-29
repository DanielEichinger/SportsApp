package com.example.sportsapp.models

import com.example.sportsapp.sql.UsersTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserSqlStore : UserStore {

    var user_id: Int = 0

    override fun create(name: String, password: String) : Boolean {
        var usernameAvailable = false

        transaction {
             usernameAvailable = UsersTable.select { UsersTable.name.eq(name) }.empty()
        }

        if (!usernameAvailable) {
            return false
        }

        transaction {
            UsersTable.insert {
                it[UsersTable.name] = name
                it[UsersTable.password] = password
            }
        }
        return true
    }

    override fun login(name: String, password: String): Boolean {
        var success = false
        transaction {
            val query: Query = UsersTable.select { UsersTable.name.eq(name) and UsersTable.password.eq(password)}
            success = !query.empty()

            // Save id of logged in user
            query.forEach { user_id = it[UsersTable.id].value }
        }
        return success
    }

    override fun getUserId(): Int = user_id
}
