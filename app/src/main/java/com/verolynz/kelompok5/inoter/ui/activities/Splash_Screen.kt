package com.verolynz.kelompok5.inoter.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.verolynz.kelompok5.inoter.R

class Splash_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            ToMainAct()
        }, 3000L)

    }
    private fun ToMainAct(){
        Intent(this,Login::class.java).also{
            startActivity(it)
            finish()
        }
    }
}