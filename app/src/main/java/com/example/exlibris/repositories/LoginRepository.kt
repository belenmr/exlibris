package com.example.exlibris.repositories

import com.example.exlibris.data.User
import com.example.exlibris.db.UserDao
import io.reactivex.disposables.CompositeDisposable

class LoginRepository(private val dataUser: UserDao,
                      private val compositeDisposable: CompositeDisposable
) : ILoginRepository{
    override fun addUser(userData: User, success: () -> Unit, error: () -> Unit) {
        compositeDisposable.add(
            dataUser
                .addUser(userData)
                .subscribe({
                    success()
                }, {
                    error()
                })
        )
    }

    override fun getUser(usuario: String, success: (User) -> Unit, error: () -> Unit) {
        compositeDisposable.add(
            dataUser
                .getUser(usuario)
                .subscribe({
                    success(it)
                }, {
                    error()
                })
        )
    }

}