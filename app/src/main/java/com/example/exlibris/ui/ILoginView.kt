package com.example.exlibris.ui

interface ILoginView {
    fun showLoading()
    fun hideLoading()
    fun onError()
    fun goToHome()
    fun usuarioInvalido()
    fun passwordInvalido()
}