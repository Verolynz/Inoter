package com.verolynz.kelompok5.inoter.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.verolynz.kelompok5.inoter.R
import com.verolynz.kelompok5.inoter.data.local.AtletDao
import com.verolynz.kelompok5.inoter.data.local.CODao
import com.verolynz.kelompok5.inoter.data.local.OlahragaDB
import com.verolynz.kelompok5.inoter.data.local.UsersDao
import com.verolynz.kelompok5.inoter.data.repositories.OlahragaRepository
import com.verolynz.kelompok5.inoter.utils.ExecutorsUtils
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var cODao: CODao
    private lateinit var atletDao: AtletDao
    private lateinit var usersDao: UsersDao
    private lateinit var executorsUtils: ExecutorsUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = OlahragaDB.getDatabase(this)
        cODao = db.CODao()
        atletDao = db.AtletDao()
        usersDao = db.UsersDao()
        executorsUtils = ExecutorsUtils()
        lifecycleScope.launch {
            OlahragaRepository.getInstance(cODao, atletDao, usersDao, executorsUtils).initializeData(this@Login)
        }
    }
}