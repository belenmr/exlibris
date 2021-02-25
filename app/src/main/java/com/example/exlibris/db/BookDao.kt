package com.example.exlibris.db

import android.content.Context
import com.example.exlibris.data.Book
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class BookDao(private val context: Context) {
    private var dao: Dao<Book, Int>

    init {
        val helper = OpenHelperManager.getHelper(context, DBHelper::class.java)
        dao = helper.getDao(Book::class.java)
    }

    fun addBook(book: Book): Completable {
        return Completable
            .fromCallable { dao.create(book) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteBook(book: Book): Completable {
        return Completable
            .fromCallable { dao.delete(book) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateBook(book: Book): Completable {
        return Completable
            .fromCallable { dao.update(book) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getBooks(): Single<List<Book>> {
        return Single
            .fromCallable { dao.queryForAll() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getBook(pathImg: String): Single<Book> {
        return Single
            .fromCallable {
                dao.queryBuilder().where().eq("ImageResource", pathImg).queryForFirst()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}