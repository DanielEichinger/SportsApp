package com.example.sportsapp.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserSqlStore : UserStore {

    var user_id: Int = 0

    override fun create(name: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun login(name: String, password: String): Boolean {
        var success = false
        transaction {
            val query: Query = UsersTable.select { UsersTable.name.eq(name) and UsersTable.password.eq(password)}
            success = !query.empty()
            query.forEach { user_id = it[UsersTable.id].value }
        }
        return success
    }

    override fun getUserId(): Int = user_id
}
