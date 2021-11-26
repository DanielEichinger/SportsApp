package com.example.sportsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sportsapp.databinding.ActivityLocationBinding
import com.example.sportsapp.main.MainApp
import com.example.sportsapp.models.Location
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i

class LocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationBinding
    var location = Location()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Location Activity started...")

        binding.buttonSubmit.setOnClickListener {
            i("Add button pressed")
            location.name = binding.name.text.toString().trim()
            location.description = binding.description.text.toString().trim()
            val kindsOfSportsString = binding.possibleSports.text.toString()

            if (location.name.isEmpty() || location.description.isEmpty() || kindsOfSportsString.isEmpty()) {
                Snackbar.make(it, "Missing information", Snackbar.LENGTH_LONG).show()
            } else {
                location.sports = kindsOfSportsString.split( "\n").toSet()

                app.locations.add(location.copy())
                for (i in app.locations.indices) {
                    i("Location[$i]: ${app.locations[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}