package com.example.exlibris.presenters

import com.example.exlibris.repositories.ILoginRepository
import com.example.exlibris.repositories.ISharePref
import com.example.exlibris.ui.ILoginView

class LoginPresenter(private val view: ILoginView,
                     private val repository: ILoginRepository,
                     private val sharedRepository: ISharePref
) : ILoginPresenter {
    private var datosValidos = true
    override fun doLogin(usuario: String, password: String) {
        view.showLoading()

        validateData(usuario, password)
        if (datosValidos){
            repository
                .getUser(usuario, {
                    if ((it.user == usuario) && (it.password == password)) {
                        sharedRepository.GetPreference("libroUsuario",{},{})
                        sharedRepository.EditPreference("libroUsuario", it.user,{},{})
                        view.hideLoading()
                        view.goToHome()
                    }

                }, {
                    view.hideLoading()
                    view.onError()
                })
        }else{
            view.hideLoading()
        }
    }


    private fun validateData(user: String, pass: String) {
        validateUser(user)
        validatePass(pass)
    }

    private fun validatePass(pass: String) {
        if (pass.isEmpty()) {
            view.passwordInvalido()
            datosValidos = false
        }
    }

    private fun validateUser(user: String) {
        if (user.isEmpty()) {
            view.usuarioInvalido()
            datosValidos = false
        }
    }

}