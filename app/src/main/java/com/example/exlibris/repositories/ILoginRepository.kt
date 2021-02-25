package com.example.exlibris.repositories

import com.example.exlibris.data.User
import com.example.exlibris.db.UserDao

interface ILoginRepository {
    fun addUser(userData : User, success:()-> Unit, error: ()-> Unit)
    fun getUser(usuario: String, success: (User) -> Unit, error: () -> Unit)
}