package com.example.sportsapp.activities

import android.content.Intent
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = LocationAdapter(app.locations, this)

        val fab = binding.fab
        fab.setOnClickListener { view ->
            val launcherIntent = Intent(this, LocationActivity::class.java)
            startActivityForResult(launcherIntent, 0)
            //app.locations.add(Location("Test", "Add", emptySet()))
        }

        val refresh = binding.swipeContainer
        refresh.setOnRefreshListener {
            //app.locations.add(Location("Test", "Update", emptySet()))
            binding.recyclerView.adapter?.notifyDataSetChanged()
            refresh.isRefreshing = false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onLocationClick(location: Location) {
        if (intent.hasExtra("from_event_creation")) {
            val resultIntent = Intent()
            resultIntent.putExtra("selected_location", location.id)
            i("id sent: ${location.id}")
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

}

interface LocationListener {
    fun onLocationClick(location: Location)
}