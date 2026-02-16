package com.example.ft_hangouts.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    //Erstellen Contact Tabelle
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_CONTACTS (
                $COLUMN_CONTACT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CONTACT_NAME TEXT NOT NULL,
                $COLUMN_CONTACT_PHONE TEXT NOT NULL,
                $COLUMN_CONTACT_EMAIL TEXT,
                $COLUMN_CONTACT_ADDRESS TEXT,
                $COLUMN_CONTACT_NOTE TEXT,
                $COLUMN_CONTACT_PICTURE_URL TEXT 
            )
            """.trimIndent()
        )
        //Erstellen Message Tabelle
        db.execSQL(
            """
            CREATE TABLE $TABLE_MESSAGES (
                $COLUMN_MESSAGE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MESSAGE_CONTACT_ID INTEGER NOT NULL,
                $COLUMN_MESSAGE_BODY TEXT NOT NULL,
                $COLUMN_MESSAGE_TIMESTAMP INTEGER NOT NULL,
                $COLUMN_MESSAGE_DIRECTION TEXT NOT NULL,
                FOREIGN KEY($COLUMN_MESSAGE_CONTACT_ID) REFERENCES $TABLE_CONTACTS($COLUMN_CONTACT_ID)
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MESSAGES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    //Erstellen Contact und Message Attribute
    companion object {
        private const val DATABASE_NAME = "ft_hangouts.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_CONTACTS = "contacts"
        const val COLUMN_CONTACT_ID = "id"
        const val COLUMN_CONTACT_NAME = "name"
        const val COLUMN_CONTACT_PHONE = "phone"
        const val COLUMN_CONTACT_EMAIL = "email"
        const val COLUMN_CONTACT_ADDRESS = "address"
        const val COLUMN_CONTACT_NOTE = "note"

        const val TABLE_MESSAGES = "messages"
        const val COLUMN_MESSAGE_ID = "id"
        const val COLUMN_MESSAGE_CONTACT_ID = "contact_id"
        const val COLUMN_MESSAGE_BODY = "body"
        const val COLUMN_MESSAGE_TIMESTAMP = "timestamp"
        const val COLUMN_MESSAGE_DIRECTION = "direction" // "IN" oder "OUT"
    }
}