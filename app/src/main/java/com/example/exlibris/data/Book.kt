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
    var name: String,
    @DatabaseField
    var author: String,
    @DatabaseField
    var publishingHouse: String,
    @DatabaseField
    var isbn: String,
    @DatabaseField
    var read: Boolean = false,
    @DatabaseField(id = true)
    val id: Int? = null
) : Parcelable {
    constructor() : this("","","","","",false,null)
}