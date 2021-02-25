package com.example.exlibris.repositories

import com.example.exlibris.data.User

interface ISharePref {

    fun GetPreference(preferenceName :String, success:()-> Unit, error: ()-> Unit)
    fun EditPreference(preferenceName :String, userName :String, success: (User) -> Unit, error: () -> Unit)
}