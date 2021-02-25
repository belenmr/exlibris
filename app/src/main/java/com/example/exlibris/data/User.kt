package com.example.exlibris.data

import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import kotlinx.android.parcel.Parcelize

@Parcelize
@DatabaseTable(tableName = "UsersData")
class User(
    @DatabaseField(id = true)
    val user: String,
    @DatabaseField
    val password: String
) : Parcelable {


    constructor() : this("", "")

}