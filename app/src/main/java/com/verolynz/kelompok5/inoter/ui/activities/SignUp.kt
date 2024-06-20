package com.verolynz.kelompok5.inoter.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.verolynz.kelompok5.inoter.R
import com.verolynz.kelompok5.inoter.data.local.OlahragaDB
import com.verolynz.kelompok5.inoter.data.repositories.OlahragaRepository
import com.verolynz.kelompok5.inoter.utils.ExecutorsUtils

class SignUp : AppCompatActivity() {
    private lateinit var olahragaRepository: OlahragaRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val loginButton = findViewById<TextView>(R.id.logins)
        Repository()
        loginButton.setOnClickListener {
            login()
        }
        signupviewholder()

    }
    private fun Repository() {
        val db = OlahragaDB.getDatabase(this)
        val cODao = db?.CODao()
        val atletDao = db?.AtletDao()
        val usersDao = db?.UsersDao()
        val executorsUtils = ExecutorsUtils()

        if (cODao != null && atletDao != null && usersDao != null && executorsUtils != null) {
            olahragaRepository =
                OlahragaRepository.getInstance(cODao, atletDao, usersDao, executorsUtils)
        }
    }
    private fun login(){

            val intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)


    }

    private fun signup(username: String, password: String) {
        olahragaRepository.buatakun(username, password)
    }

    private fun signupviewholder() {
        val usernameEditText = findViewById<TextInputEditText>(R.id.username_edit)
        val passwordEditText = findViewById<TextInputEditText>(R.id.password_edit)
        val loginButton = findViewById<MaterialButton>(R.id.login_button)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            signup(username, password)
            val intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()

        }
    }

}