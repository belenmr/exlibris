package com.example.exlibris.db

import android.content.Context
import com.example.exlibris.data.Book
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io

class BookDao(private val context: Context) {
    private var dao: Dao<Book, Int>

    init {
        val helper = OpenHelperManager.getHelper(context, DBHelper::class.java)
        dao = helper.getDao(Book::class.java)
    }

    fun addBook(book: Book) = dao.create(book)

    fun deleteBook(book: Book) = dao.delete(book)

    fun updateBook(book: Book) = dao.update(book)

    fun getBook() = dao.queryForAll()

    fun getBooks(bookId: Int) = dao.queryForId(bookId)
}