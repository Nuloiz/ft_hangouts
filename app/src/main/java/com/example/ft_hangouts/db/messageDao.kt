package com.example.fthangouts.db

import androidx.room.*

@Dao //Data Access Object: bündelt zentrale SQL-Abfragen zu Contact und lässt sie einfacher callen
interface MessageDao {

    @Query("SELECT * FROM messages WHERE contactId = :contactId ORDER BY timestamp ASC")
    suspend fun getMessagesForContact(contactId: Long): List<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<Message>)

    @Query("DELETE FROM messages WHERE contactId = :contactId")
    suspend fun deleteMessagesForContact(contactId: Long)
}
