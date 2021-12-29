package com.example.sportsapp.models

import com.example.sportsapp.sql.ChatMessagesTable
import com.example.sportsapp.sql.UsersTable
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class ChatMessageSqlStore: ChatMessageStore {

    val chatMessages : ArrayList<ChatMessage> = ArrayList()

    override fun getAll(e_id : Int): ArrayList<ChatMessage> {
        chatMessages.clear()
        transaction {
            val chatQuery = ChatMessagesTable.join(UsersTable, JoinType.INNER, ChatMessagesTable.u_id, UsersTable.id)
                .select { ChatMessagesTable.e_id.eq(e_id) }
                .orderBy(ChatMessagesTable.time, SortOrder.ASC)
                .forEach { chatMessages.add(ChatMessage(it[ChatMessagesTable.id].value,
                    it[ChatMessagesTable.mesage],
                    User(it[ChatMessagesTable.u_id], it[UsersTable.name]),
                    it[ChatMessagesTable.time])) }
        }
        return chatMessages
    }

    override fun create(ev_id: Int, chatMessage: ChatMessage) {
        transaction {
            ChatMessagesTable.insert {
                it[mesage] = chatMessage.message
                it[u_id] = chatMessage.user.id
                it[e_id] = ev_id
                it[time] = chatMessage.time
            }
        }
    }
}