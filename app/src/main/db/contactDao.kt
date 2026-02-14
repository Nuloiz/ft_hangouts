package com.contactdao.fthangouts.db

import androidx.room.*

@Dao //Data Access Object: bündelt zentrale SQL-Abfragen zu Contact und lässt sie einfacher callen
interface ContactDao {

    @Query("SELECT * FROM contacts ORDER BY name ASC")
    suspend fun getAllContacts(): List<Contact>
    @Query("SELECT * FROM contacts WHERE id = :id")
    suspend fun getContactById(id: Long): Contact?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact): Long

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)
}
