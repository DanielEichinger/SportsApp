package com.example.sportsapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsapp.R
import com.example.sportsapp.databinding.ActivityLoginBinding
import com.example.sportsapp.main.MainApp
import com.example.sportsapp.models.User
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import timber.log.Timber.i

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var app : MainApp

    private lateinit var registerIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Login Activity started...")

        binding.buttonLogin.setOnClickListener {
            if (app.user.login(binding.username.text.toString(), binding.password.text.toString())) {
                val launcherIntent = Intent(this, EventListActivity::class.java)
                startActivity(launcherIntent)
            } else {
                Snackbar.make(it, "Wrong input", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.buttonLoginRegister.setOnClickListener {
            val launcherIntent = Intent(this, RegisterActivity::class.java)
            startActivity(launcherIntent)
        }

        registerRegisterCallback()
    }

    private fun registerRegisterCallback() {
        registerIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // nothing to do here
        }
    }
}
