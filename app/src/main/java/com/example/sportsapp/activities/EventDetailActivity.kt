package com.example.sportsapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsapp.databinding.ActivityEventDetailBinding
import com.example.sportsapp.main.MainApp
import com.example.sportsapp.models.Event
import timber.log.Timber.i

class EventDetailActivity : AppCompatActivity(){

    private lateinit var binding: ActivityEventDetailBinding
    var event = Event()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Event Detail Activity started")

        if(intent.hasExtra("selected_event")) {
            event = intent.extras?.getParcelable("selected_event")!!
            binding.name.text = event.title
            binding.description.text = event.description
        }

    }

}