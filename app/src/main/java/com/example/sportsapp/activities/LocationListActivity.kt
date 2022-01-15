package com.example.sportsapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsapp.R
import com.example.sportsapp.adapter.LocationAdapter
import com.example.sportsapp.databinding.ActivityLocationListBinding
import com.example.sportsapp.main.MainApp
import com.example.sportsapp.models.GpsLocation
import com.example.sportsapp.models.Location
import timber.log.Timber.i

class LocationListActivity : AppCompatActivity(), LocationListener{

    lateinit var app: MainApp
    private lateinit var binding: ActivityLocationListBinding

    private lateinit var refreshLauncherIntent : ActivityResultLauncher<Intent>
    private lateinit var showLocationsMapLauncherIntent: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationListBinding.inflate(layoutInflater)
        binding.toolbar.title = getString(R.string.location_list_toolbar_title)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = LocationAdapter(app.locations.getAll(), this)

        val fab = binding.fab
        fab.setOnClickListener {
            val launcherIntent = Intent(this, LocationActivity::class.java)
            refreshLauncherIntent.launch(launcherIntent)
        }

        val refresh = binding.swipeContainer
        refresh.setOnRefreshListener {
            binding.recyclerView.adapter = LocationAdapter(app.locations.getAll(), this)
            binding.recyclerView.adapter?.notifyDataSetChanged()
            refresh.isRefreshing = false
        }

        registerRefreshCallback()
        registerShowLocationsMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_location_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onLocationClick(location: Location) {
        if (intent.hasExtra("from_event_creation")) {
            val resultIntent = Intent()
            resultIntent.putExtra("selected_location", location)
            i("selected location: $location")
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // back buttons on other activities work without this, somehow not here
            }
            R.id.item_locations_map -> {
                val launcherIntent = Intent(this, MapActivity::class.java)
                // value of this extra is not important
                // it is only used to tell MapActivity to use location list from MainApp
                launcherIntent.putExtra("location_list", 0)
                showLocationsMapLauncherIntent.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerRefreshCallback() {
        refreshLauncherIntent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun registerShowLocationsMapCallback() {
        showLocationsMapLauncherIntent = registerForActivityResult((ActivityResultContracts.StartActivityForResult())) {
            i("Map for multiple locations closed")
        }
    }
}

interface LocationListener {
    fun onLocationClick(location: Location)
}