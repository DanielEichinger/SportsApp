package com.example.sportsapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsapp.adapter.ChatMessageAdapter
import com.example.sportsapp.databinding.ActivityEventDetailBinding
import com.example.sportsapp.main.MainApp
import com.example.sportsapp.models.ChatMessage
import com.example.sportsapp.models.Event
import timber.log.Timber.i
import java.time.LocalDateTime

class EventDetailActivity : AppCompatActivity(){

    private lateinit var binding: ActivityEventDetailBinding
    var event = Event()
    var chatMessage = ChatMessage()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        i("Event Detail Activity started")

        if(intent.hasExtra("selected_event")) {
            event = intent.extras?.getParcelable("selected_event")!!
            //binding.recyclerView.adapter = ChatMessageAdapter(event.chatHistory)
            binding.toolbar.title = event.title
            binding.description.text = event.description
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.button.setOnClickListener {
            chatMessage.message = binding.message.text.toString()
            chatMessage.user.username = app.user.username
            chatMessage.time = LocalDateTime.now()
            //event.chatHistory.add(chatMessage.copy())
            binding.recyclerView.adapter?.notifyDataSetChanged()

        }
    }
}