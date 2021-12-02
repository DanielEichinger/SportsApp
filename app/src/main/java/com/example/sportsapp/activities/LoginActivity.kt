package com.example.sportsapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsapp.databinding.ActivityLoginBinding
import com.example.sportsapp.main.MainApp
import com.example.sportsapp.models.User
import timber.log.Timber.i

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Login Activity started...")

        binding.buttonLogin.setOnClickListener {
            app.user = User(binding.username.text.toString())
            val launcherIntent = Intent(this, EventActivity::class.java)
            startActivity(launcherIntent)
        }
    }
}