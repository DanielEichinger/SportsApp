package com.example.sportsapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsapp.R
import com.example.sportsapp.adapter.ChatMessageAdapter
import com.example.sportsapp.databinding.ActivityEventDetailBinding
import com.example.sportsapp.main.MainApp
import com.example.sportsapp.models.ChatMessage
import com.example.sportsapp.models.Event
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EventDetailActivity : AppCompatActivity(){

    private lateinit var binding: ActivityEventDetailBinding
    var event = Event()
    var chatMessage = ChatMessage()
    lateinit var app: MainApp
    private lateinit var editEventIntentLauncher : ActivityResultLauncher<Intent>

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
            binding.recyclerView.adapter = ChatMessageAdapter(event.chatHistory)
            binding.toolbar.title = event.title
            binding.description.text = event.description
            event.dateTime
            binding.admin.text = getString(R.string.event_detail_admin, event.admin.username)
            binding.location.text = getString(R.string.event_detail_location_time,
                event.location.name, event.dateTime.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")))
            event.admin
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.buttonComment.setOnClickListener {
            chatMessage.message = binding.message.text.toString()
            chatMessage.user.id = app.user.getUserId()
            chatMessage.time = LocalDateTime.now()
            app.chatMessages.create(event.id, chatMessage)
            binding.recyclerView.adapter = ChatMessageAdapter(app.chatMessages.getAll(event.id))
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.message.setText("")
            binding.message.clearFocus()
        }

        editEventCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_event_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_edit_event -> {
                val parentLayout: View = findViewById(R.id.appBarLayout)
                if (app.user.getUserId() == event.admin.id) {
                    val launcherIntent = Intent(this, EventActivity::class.java)
                    launcherIntent.putExtra("event_edit", event)
                    editEventIntentLauncher.launch(launcherIntent)
                } else {
                    Snackbar.make(parentLayout, getString(R.string.event_detail_edit_not_admin), Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.item_join_event -> {
                val parentLayout: View = findViewById(R.id.appBarLayout)
                if (app.user.getUserId() == event.admin.id) {
                    Snackbar.make(parentLayout, getString(R.string.event_detail_join_admin), Snackbar.LENGTH_LONG).show()
                } else {
                    // Join event when not already, otherwise leave event
                    val joined = app.events.addParticipant(app.user.getUserId(), event.id)
                    if (joined) {
                        Snackbar.make(parentLayout, getString(R.string.event_detail_joined), Snackbar.LENGTH_LONG).show()
                    } else {
                        Snackbar.make(parentLayout, getString(R.string.event_detail_left), Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editEventCallback() {
        editEventIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            i("Done Editing")
        }
    }
}