package com.example.exlibris.data

import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import kotlinx.android.parcel.Parcelize

@DatabaseTable(tableName = "Books")
@Parcelize
class Book (
    @DatabaseField(columnName = "ImageResource")
    val resImage: String,
    @DatabaseField
    val name: String,
    @DatabaseField
    val author: String,
    @DatabaseField
    val publishingHouse: String,
    @DatabaseField
    val isbn: String,
    @DatabaseField
    var read: Boolean = false,
    @DatabaseField(id = true)
    val id: Int? = null
) : Parcelable {
    constructor() : this("","","","","",false,null)
}