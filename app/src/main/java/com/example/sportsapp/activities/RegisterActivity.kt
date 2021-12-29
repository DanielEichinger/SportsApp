package com.example.sportsapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsapp.R
import com.example.sportsapp.databinding.ActivityRegisterBinding
import com.example.sportsapp.main.MainApp
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber.i

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Register Activity started...")

        Picasso.get()
            .load(R.drawable.default_avatar)        // loading and displaying image without
            .placeholder(R.drawable.default_avatar) // placeholder does not work
            .into(binding.imageView)

        binding.buttonRegister.setOnClickListener {
            i("Register button pressed")
            if (binding.registerPassword.text.toString() != binding.registerPasswordRepeat.text.toString()) {
                Snackbar.make(it, "Passwords do not match", Snackbar.LENGTH_LONG).show()
            } else {
                val success = app.user.create(binding.registerUsername.text.toString(),
                    binding.registerPassword.text.toString())
                if (!success) {
                    Snackbar.make(it, "Username already taken", Snackbar.LENGTH_LONG).show()
                } else {
                    finish()
                }
            }
        }
    }
}