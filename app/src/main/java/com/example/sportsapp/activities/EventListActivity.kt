package com.example.sportsapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsapp.R
import com.example.sportsapp.adapter.EventAdapter
import com.example.sportsapp.databinding.ActivityEventListBinding
import com.example.sportsapp.main.MainApp
import com.example.sportsapp.models.Event
import timber.log.Timber.i

class EventListActivity : AppCompatActivity(), EventListener{
    lateinit var app: MainApp
    private lateinit var binding: ActivityEventListBinding

    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var detailIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = getString(R.string.event_list_toolbar_title)
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewEvents.layoutManager = layoutManager
        binding.recyclerViewEvents.adapter = EventAdapter(app.events, this)

        val fab = binding.fabEvents
        fab.setOnClickListener {
            val launcherIntent = Intent(this, EventActivity::class.java)
            refreshIntentLauncher.launch(launcherIntent)
        }

        registerRefreshCallback()
        registerDetailCallback()
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            binding.recyclerViewEvents.adapter?.notifyDataSetChanged()
        }
    }

    private fun registerDetailCallback() {
        detailIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // nothing to do here
        }

    }

    override fun onEventClick(event: Event) {
        i("selected Event: $event")
        val launcherIntent = Intent(this, EventDetailActivity::class.java)
        launcherIntent.putExtra("selected_event", event)
        detailIntentLauncher.launch(launcherIntent)
    }
}

interface EventListener {
    fun onEventClick(event: Event)
}