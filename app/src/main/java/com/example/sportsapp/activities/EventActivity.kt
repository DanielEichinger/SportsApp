package com.example.sportsapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsapp.databinding.ActivityEventBinding
import com.example.sportsapp.fragments.DatePickerFragment
import com.example.sportsapp.fragments.TimePickerFragment
import com.example.sportsapp.main.MainApp
import timber.log.Timber.i
import com.example.sportsapp.models.Event
import com.example.sportsapp.models.Location
import com.google.android.material.snackbar.Snackbar

class EventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventBinding
    var event = Event()
    lateinit var app : MainApp

    private lateinit var selectLocationIntentLauncher: ActivityResultLauncher<Intent>

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
            selectLocationIntentLauncher.launch(launcherIntent)
        }

        binding.buttonSubmitEvent.setOnClickListener {
            event.title = binding.eventTitle.text.toString()
            event.description = binding.eventDescription.text.toString()
            event.admin?.username = app.user.username

            if (event.title.isEmpty() || event.description.isEmpty()) {
                Snackbar.make(it, "Missing information", Snackbar.LENGTH_LONG)
            } else {
                app.events.add(event.copy())
            }
            setResult(RESULT_OK)
            finish()
        }

        registerSelectLocationCallback()
    }

    private fun registerSelectLocationCallback() {
        selectLocationIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> when (result.resultCode) {
                RESULT_OK -> {
                    if(result.data != null) {
                        val selected_location = result.data!!.extras?.getParcelable<Location>("selected_location")
                        i("Selected location: $selected_location")
                        event.location = selected_location!!
                        binding.labelSelectedLocation.text = selected_location.name
                    }
                }
                RESULT_CANCELED -> {}
                else -> {}
            }
        }
    }


    fun showTimePickerDialog(v: View) {
        TimePickerFragment().show(supportFragmentManager, "timePicker")
    }

    fun showDatePickerDialog(v: View) {
        DatePickerFragment().show(supportFragmentManager, "datePicker")
    }
}