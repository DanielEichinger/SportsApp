package com.example.sportsapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsapp.adapter.LocationAdapter
import com.example.sportsapp.databinding.ActivityLocationListBinding
import com.example.sportsapp.main.MainApp

class LocationListActivity : AppCompatActivity(){

    lateinit var app: MainApp
    private lateinit var binding: ActivityLocationListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = LocationAdapter(app.locations)

        val fab = binding.fab
        fab.setOnClickListener { view ->
            val launcherIntent = Intent(this, LocationActivity::class.java)
            startActivityForResult(launcherIntent, 0)
        }
    }


}