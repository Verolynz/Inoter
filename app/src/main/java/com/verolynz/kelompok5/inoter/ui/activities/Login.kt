package com.verolynz.kelompok5.inoter.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.verolynz.kelompok5.inoter.R
import com.verolynz.kelompok5.inoter.data.local.AtletDao
import com.verolynz.kelompok5.inoter.data.local.CODao
import com.verolynz.kelompok5.inoter.data.local.OlahragaDB
import com.verolynz.kelompok5.inoter.data.local.UsersDao
import com.verolynz.kelompok5.inoter.data.repositories.OlahragaRepository
import com.verolynz.kelompok5.inoter.utils.ExecutorsUtils
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var olahragaRepository: OlahragaRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Repository()
        loginviewholder()
        generateadmin()
        generateuser()

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

    private fun loginviewholder() {
        val usernameEditText = findViewById<TextInputEditText>(R.id.username_edit)
        val passwordEditText = findViewById<TextInputEditText>(R.id.password_edit)
        val loginButton = findViewById<MaterialButton>(R.id.login_button)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            login(username, password)

        }
    }

    private fun login(username: String, password: String) {
        olahragaRepository.loginAuth(username, password, this)
    }
    private fun generateadmin() {
        olahragaRepository.checkAndCreateAdmin()
    }
    private fun generateuser() {
        olahragaRepository.checkAndCreateTest()
    }
}