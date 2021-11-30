package com.example.sportsapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsapp.databinding.ActivityEventBinding
import com.example.sportsapp.main.MainApp
import timber.log.Timber.i
import com.example.sportsapp.models.Event

class EventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventBinding
    var event = Event()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Event Activity started...")

        binding.buttonLocationList.setOnClickListener {
            i("Location button pressed")
            val launcherIntent = Intent(this, LocationListActivity::class.java)
            launcherIntent.putExtra("from_event_creation", true)
            startActivityForResult(launcherIntent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> {
                if (resultCode == RESULT_OK) {
                    val selected_id = data?.getIntExtra("selected_location", -1)
                    i("id received: ${selected_id}")
                    event.location = app.locations[selected_id!!]
                    i("Event: ${event}")
                    binding.labelSelectedLocation.text = event.location.name
                }
            }
        }
    }

}