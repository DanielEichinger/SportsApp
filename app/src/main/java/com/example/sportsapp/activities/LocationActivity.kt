package com.example.sportsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.sportsapp.databinding.ActivityLocationBinding
import com.example.sportsapp.main.MainApp
import com.example.sportsapp.models.Location
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i

class LocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationBinding
    var location = Location()
    lateinit var app : MainApp
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Location Activity started...")

        binding.buttonLocation.setOnClickListener {
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location.GpsLoc)
            mapIntentLauncher.launch(launcherIntent)
        }

        binding.buttonSubmit.setOnClickListener {
            i("Add button pressed")
            location.name = binding.name.text.toString().trim()
            location.description = binding.description.text.toString().trim()
            val kindsOfSportsString = binding.possibleSports.text.toString()

            if (location.name.isEmpty() || location.description.isEmpty() || kindsOfSportsString.isEmpty()) {
                Snackbar.make(it, "Missing information", Snackbar.LENGTH_LONG).show()
            } else {
                location.sports = kindsOfSportsString.split( "\n").toMutableSet()
                app.locations.create(location.copy())

                setResult(RESULT_OK)
                finish()
            }
        }

        registerMapCallback()
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if(result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            location.GpsLoc = result.data!!.extras?.getParcelable("location")!!
                            i("Location == ${location.GpsLoc}")
                        }
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}