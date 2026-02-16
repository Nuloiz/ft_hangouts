package com.example.ft_hangouts.db

import android.content.ContentValues
import android.content.Context

class ContactDao(context: Context)
{
    private val dbHelper = DatabaseHelper(context)
    fun getAllContacts(): List<Contact>
    {
        val db = dbHelper.readableDatabase
        val contacts = mutableListOf<Contact>()
        val cursor = db.query( //query hat viele Parameter die nicht immer benötigt werden, daher null
            DatabaseHelper.TABLE_CONTACTS,  //table
            null,                           //columns
            null,                           //selection
            null,                           //selectionArgs
            null,                           //groupBy
            null,                           //having
            "${DatabaseHelper.COLUMN_CONTACT_NAME} ASC"
        )
        cursor.use { //cursor sind die eingebenen Parameter
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT_ID))
                val name = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT_NAME))
                val phone = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT_PHONE))
                val email = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT_EMAIL))
                val address = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT_ADDRESS))
                val note = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT_NOTE))
                val pictureurl = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT_PICTUREURL))

                contacts.add(
                    Contact(
                        id = id,
                        name = name,
                        phoneNumber = phone,
                        email = email,
                        address = address,
                        note = note,
                        pictureurl = pictureurl
                    )
                )
            }
        }
        return contacts
    }

    fun getContactById(id: Long): Contact?
    {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_CONTACTS,
            null,
            "${DatabaseHelper.COLUMN_CONTACT_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        cursor.use {
            if (it.moveToFirst()) {
                val name = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT_NAME))
                val phone = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT_PHONE))
                val email = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT_EMAIL))
                val address = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT_ADDRESS))
                val note = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT_NOTE))
                val pictureurl = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT_PICTUREURL))

                return Contact(
                    id = id,
                    name = name,
                    phoneNumber = phone,
                    email = email,
                    address = address,
                    note = note,
                    pictureurl = pictureurl
                )
            }
        }
        return null
    }

    fun insertContact(contact: Contact): Long
    {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_CONTACT_NAME, contact.name)
            put(DatabaseHelper.COLUMN_CONTACT_PHONE, contact.phoneNumber)
            put(DatabaseHelper.COLUMN_CONTACT_EMAIL, contact.email)
            put(DatabaseHelper.COLUMN_CONTACT_ADDRESS, contact.address)
            put(DatabaseHelper.COLUMN_CONTACT_NOTE, contact.note)
            put(DatabaseHelper.COLUMN_CONTACT_PICTUREURL, contact.pictureurl)
        }
        return db.insert(DatabaseHelper.TABLE_CONTACTS, null, values)
    }

    fun updateContact(contact: Contact): Int
    {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_CONTACT_NAME, contact.name)
            put(DatabaseHelper.COLUMN_CONTACT_PHONE, contact.phoneNumber)
            put(DatabaseHelper.COLUMN_CONTACT_EMAIL, contact.email)
            put(DatabaseHelper.COLUMN_CONTACT_ADDRESS, contact.address)
            put(DatabaseHelper.COLUMN_CONTACT_NOTE, contact.note)
            put(DatabaseHelper.COLUMN_CONTACT_PICTUREURL, contact.pictureurl)
        }
        return db.update(
            DatabaseHelper.TABLE_CONTACTS,
            values,
            "${DatabaseHelper.COLUMN_CONTACT_ID} = ?",
            arrayOf(contact.id.toString())
        )
    }

    fun deleteContact(id: Long): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseHelper.TABLE_CONTACTS,
            "${DatabaseHelper.COLUMN_CONTACT_ID} = ?",
            arrayOf(id.toString())
        )
    }
}

