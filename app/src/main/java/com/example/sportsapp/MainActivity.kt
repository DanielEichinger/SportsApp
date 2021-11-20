package com.example.sportsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sportsapp.databinding.ActivityMainBinding
import com.example.sportsapp.models.SportLocation
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import timber.log.Timber.i

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var sportsLocation = SportLocation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("App started...")

        binding.buttonSubmit.setOnClickListener {
            i("Hinzufügen gedrückt")
            sportsLocation.name = binding.name.text.toString().trim()
            sportsLocation.description = binding.description.text.toString().trim()
            val kindsOfSportsString = binding.possibleSports.text.toString()
            if(sportsLocation.name.isEmpty() || sportsLocation.description.isEmpty() || kindsOfSportsString.isEmpty()){
                Snackbar.make(it, "Missing information", Snackbar.LENGTH_LONG).show()
            } else {
                sportsLocation.kindsOfSports = kindsOfSportsString.split( "\n").toSet()
                Snackbar.make(it, "Ok", Snackbar.LENGTH_LONG).show()
            }
            i("$sportsLocation")
        }
    }
}