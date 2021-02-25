package com.example.exlibris.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.exlibris.R
import com.example.exlibris.db.UserDao
import com.example.exlibris.presenters.ILoginPresenter
import com.example.exlibris.presenters.LoginPresenter
import com.example.exlibris.repositories.LoginRepository
import com.example.exlibris.repositories.SharePref
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginActivity : AppCompatActivity(), ILoginView {

    private lateinit var eTusuario: EditText
    private lateinit var eTpassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private val compositeDisposable = CompositeDisposable()
    private lateinit var preferences : SharedPreferences

    private val presenter: ILoginPresenter by lazy {
        LoginPresenter(
            this,
            LoginRepository(
                UserDao(this@LoginActivity.applicationContext),
                compositeDisposable
            ),
            SharePref(preferences, this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupUI()
    }

    private fun setupUI() {
        eTusuario = findViewById(R.id.eTusuario)
        eTpassword = findViewById(R.id.eTpassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        btnLogin.setOnClickListener { login() }
        btnRegister.setOnClickListener { register() }
    }

    private fun register() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun login() {
        presenter.doLogin(eTusuario.toString(), eTpassword.toString())
    }

    override fun showLoading() {
        TODO("Not yet implemented")
        /*
        progressBarLog.visibility = View.VISIBLE
        logingBackgroun.visibility = View.VISIBLE
        containerLog.visibility = View.GONE
        */
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
        /*progressBarLog.visibility = View.GONE
        logingBackgroun.visibility = View.GONE
        containerLog.visibility = View.VISIBLE*/
    }

    override fun onError() {
        Toast.makeText(this, getString(R.string.error_login), Toast.LENGTH_LONG).show()
    }

    override fun goToHome() {
        finish()
    }

    override fun usuarioInvalido() {
        eTusuario.error = getString(R.string.error_login_usuario)
    }

    override fun passwordInvalido() {
        eTpassword.error = getString(R.string.error_login_password)
    }

    private fun getSharedPref() {
        Single.fromCallable {
            PreferenceManager.getDefaultSharedPreferences(this)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<SharedPreferences> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(sharedPref: SharedPreferences) {
                    preferences = sharedPref
                }

                override fun onError(e: Throwable) {
                    Log.i("LoginActivity", "Error al obtener preferencias ", e)
                }
            })
    }

}