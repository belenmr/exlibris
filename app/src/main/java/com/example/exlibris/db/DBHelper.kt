package com.example.exlibris.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.exlibris.data.Book
import com.example.exlibris.data.User
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

class DBHelper (context: Context) : OrmLiteSqliteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.createTable(connectionSource, Book::class.java)
        TableUtils.createTableIfNotExists(connectionSource, User::class.java)
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        if (oldVersion == 1 && newVersion == 2) {
            TableUtils.dropTable<Book,String>(connectionSource,Book::class.java, true)
            onCreate(database,connectionSource)
        }
    }
}