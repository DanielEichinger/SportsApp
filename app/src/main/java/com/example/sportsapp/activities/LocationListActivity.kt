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
import com.example.sportsapp.models.Location
import timber.log.Timber.i

class LocationListActivity : AppCompatActivity(), LocationListener{

    lateinit var app: MainApp
    private lateinit var binding: ActivityLocationListBinding

    private lateinit var refreshLauncherIntent : ActivityResultLauncher<Intent>

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
            val l = app.locations.getAll()
            for (loc in l) {
                i("LOL: ${loc.toString()}")
            }
            binding.recyclerView.adapter = LocationAdapter(l, this)
            binding.recyclerView.adapter?.notifyDataSetChanged()
            refresh.isRefreshing = false
        }

        registerRefreshCallback()
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
                onBackPressed() // other back buttons work without this, somehow this does not
            }
            R.id.item_show_location_list -> {
                i("Map clicked!")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerRefreshCallback() {
        refreshLauncherIntent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }

}

interface LocationListener {
    fun onLocationClick(location: Location)
}