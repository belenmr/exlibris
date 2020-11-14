package com.example.exlibris.db

import android.content.Context
import com.example.exlibris.data.Book
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao


class BookDao(private val context: Context) {
    private var dao: Dao<Book, Int>

    init {
        val helper = OpenHelperManager.getHelper(context, DBHelper::class.java)
        dao = helper.getDao(Book::class.java)
    }

    fun addBook(book: Book) = dao.create(book)

    fun deleteBook(book: Book) = dao.delete(book)

    fun updateBook(book: Book) = dao.update(book)

    fun getBooks() = dao.queryForAll()

    fun getBook(bookId: Int) = dao.queryForId(bookId)

}