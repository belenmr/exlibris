package com.example.exlibris.network

import com.example.exlibris.data.BookResponse
import retrofit2.Call
import retrofit2.http.GET

interface BooksApi {
    @GET("/books")
    fun getNewBooks(): Call<List<BookResponse>>
}