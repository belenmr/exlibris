package com.example.exlibris.presenters

import com.example.exlibris.data.User
import com.example.exlibris.repositories.ILoginRepository
import com.example.exlibris.ui.IRegisterView

class RegistrerPresenter(private val registerView: IRegisterView,
                         private val repository: ILoginRepository) : IRegistrerPresenter {
    private var isUserDataValid: Boolean = true
    override fun doAddUser(usuario: String, password1: String, password2: String) {

        registerView.showSaving()
        validateData(usuario, password1, password2)
        if (isUserDataValid) {
            val userData = createUserData(usuario, password1)
            repository
                .addUser(userData, {
                    registerView.hideSaving()
                    registerView.goBack()
                }, {
                    registerView.hideSaving()
                    registerView.MensajeError()
                })
        }
        else{
            registerView.hideSaving()
        }
    }

    private fun validateData(user: String, pass1: String, pass2: String) {
        isUserDataValid = true
        validateUser(user)
        validatePass1(pass1)
        validatePass2(pass1, pass2)
    }

    private fun validatePass2(password1: String, password2: String) {
        if (password1 != password2) {
            registerView.passwordInvalido()
            isUserDataValid = false
        }
    }

    private fun validatePass1(password1: String) {
        if (password1.isEmpty() || password1.length < 4) {
            registerView.password2Invalido()
            isUserDataValid = false
        }
    }

    private fun validateUser(usuario: String) {
        if (usuario.isEmpty()) {
            registerView.usuarioInvalido()
            isUserDataValid = false
        }
    }

    private fun createUserData(usuario: String, password1: String): User {
        return User(usuario, password1)
    }

}