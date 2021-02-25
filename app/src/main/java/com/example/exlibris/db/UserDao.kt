package com.example.exlibris.db

import android.content.Context
import com.example.exlibris.data.User
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserDao(context: Context) {
    private var dao: Dao<User, String>

    init {
        val helper = OpenHelperManager.getHelper(context, DBHelper::class.java)
        dao = helper.getDao(User::class.java)
    }

    fun addUser(userData: User): Completable {
        return Completable
            .fromCallable { dao.create(userData) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUser(usuario: String) : Single<User> {
        return Single
            .fromCallable{dao.queryForId(usuario)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}