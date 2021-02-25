package com.example.exlibris.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.exlibris.R
import com.example.exlibris.db.UserDao
import com.example.exlibris.presenters.IRegistrerPresenter
import com.example.exlibris.presenters.RegistrerPresenter
import com.example.exlibris.repositories.LoginRepository
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), IRegisterView {

    private lateinit var eTusuario: EditText
    private lateinit var eTpassword1: EditText
    private lateinit var eTpassword2: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnCancel: Button
    private val compositeDisposable = CompositeDisposable()

    private val presenter: IRegistrerPresenter by lazy {
        RegistrerPresenter(
            this,
            LoginRepository(
                UserDao(this@RegisterActivity.applicationContext),
                compositeDisposable
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    private fun setupUI() {
        eTusuario = findViewById(R.id.eTusuario)
        eTpassword1 = findViewById(R.id.eTpassword)
        eTpassword2 = findViewById(R.id.eTpassword2)
        btnRegister = findViewById(R.id.btnRegister)
        btnCancel = findViewById(R.id.btnCancel)
        btnCancel.setOnClickListener { goBack() }
        btnRegister.setOnClickListener { addUser() }
    }

    private fun addUser() {
        presenter.doAddUser(eTusuario.text.toString(), eTpassword1.text.toString(), eTpassword2.text.toString())
    }

    override fun showSaving() {
        /*progressBar.visibility = View.VISIBLE
        savingBackground.visibility = View.VISIBLE
        container.visibility = View.GONE*/
    }

    override fun hideSaving() {
        /*progressBar.visibility = View.GONE
        savingBackground.visibility = View.GONE
        container.visibility = View.VISIBLE*/
    }

    override fun goBack() {
        finish()
    }

    override fun MensajeError() {
        Toast.makeText(this, getString(R.string.error_registro), Toast.LENGTH_LONG).show()
    }

    override fun usuarioInvalido() {
        eTusuario.error = getString(R.string.error_registro_usuario)

    }

    override fun passwordInvalido() {
        eTpassword1.error = getString(R.string.error_registro_password1)
    }

    override fun password2Invalido() {
        eTpassword2.error = getString(R.string.error_login_password2)
    }
}