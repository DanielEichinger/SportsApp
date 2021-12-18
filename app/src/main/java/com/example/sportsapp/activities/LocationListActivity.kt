package com.example.sportsapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = LocationAdapter(app.locations, this)

        val fab = binding.fab
        fab.setOnClickListener {
            val launcherIntent = Intent(this, LocationActivity::class.java)
            refreshLauncherIntent.launch(launcherIntent)
        }

        val refresh = binding.swipeContainer
        refresh.setOnRefreshListener {
            binding.recyclerView.adapter?.notifyDataSetChanged()
            refresh.isRefreshing = false
        }

        registerRefreshCallback()
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

    private fun registerRefreshCallback() {
        refreshLauncherIntent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }

}

interface LocationListener {
    fun onLocationClick(location: Location)
}