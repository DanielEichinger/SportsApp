package com.example.sportsapp.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsapp.R
import com.example.sportsapp.databinding.ActivityEventBinding
import com.example.sportsapp.main.MainApp
import timber.log.Timber.i
import com.example.sportsapp.models.Event
import com.example.sportsapp.models.Location
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.util.*

class EventActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener{

    private lateinit var binding: ActivityEventBinding
    var event = Event()
    lateinit var app : MainApp

    private lateinit var selectLocationIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = getString(R.string.event_creation_toolbar_title)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

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
            event.admin.username = app.user.username

            if (event.title.isEmpty() || event.description.isEmpty()) {
                Snackbar.make(it, "Missing information", Snackbar.LENGTH_LONG).show()
            } else {
                app.events.add(event.copy())
                setResult(RESULT_OK)
                finish()
            }

        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.buttonTime.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            TimePickerDialog(this, this, hour, minute, DateFormat.is24HourFormat(this)).show()
        }

        binding.buttonDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, this, year, month, day).show()
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

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        i("Time set to $hourOfDay:$minute")

        val year = event.dateTime.year
        val month = event.dateTime.monthValue
        val day = event.dateTime.dayOfMonth

        event.dateTime = LocalDateTime.of(year, month, day, hourOfDay, minute)

        binding.labelTime.text = getString(R.string.input_event_time_placeholder,
            hourOfDay.toString().padStart(2, '0'),
            minute.toString().padStart(2, '0'))

        i("dateTime: ${event.dateTime}")
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // month is in range 0..11
        // +1 for use when creating new date
        val month_correct = month + 1

        i("Date set to $dayOfMonth.$month_correct.$year")

        val hour = event.dateTime.hour
        val minute = event.dateTime.minute

        event.dateTime = LocalDateTime.of(year, month_correct, dayOfMonth, hour, minute)

        binding.labelDate.text = getString(R.string.input_event_date_placeholder,
            dayOfMonth.toString().padStart(2, '0'),
            month_correct.toString().padStart(2, '0'),
            year)

        i("dateTime: ${event.dateTime}")
    }
}