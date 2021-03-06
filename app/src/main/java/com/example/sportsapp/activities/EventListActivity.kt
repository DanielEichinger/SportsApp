package com.example.sportsapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    private lateinit var locationListIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = getString(R.string.event_list_toolbar_title)
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewEvents.layoutManager = layoutManager
        binding.recyclerViewEvents.adapter = EventAdapter(app.events.getAll(), this)

        val fab = binding.fabEvents
        fab.setOnClickListener {
            val launcherIntent = Intent(this, EventActivity::class.java)
            refreshIntentLauncher.launch(launcherIntent)
        }

        val refresh = binding.swipeContainer
        refresh.setOnRefreshListener {
            val events = app.events.getAll()
            for (event in events) {
                i("$event")
            }
            binding.recyclerViewEvents.adapter = EventAdapter(events, this)
            binding.recyclerViewEvents.adapter?.notifyDataSetChanged()
            refresh.isRefreshing = false
        }

        registerRefreshCallback()
        registerDetailCallback()
        registerlocationListCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_event_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_show_location_list -> {
                val launcherIntent = Intent(this, LocationListActivity::class.java)
                locationListIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
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

    private fun registerlocationListCallback() {
        locationListIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
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