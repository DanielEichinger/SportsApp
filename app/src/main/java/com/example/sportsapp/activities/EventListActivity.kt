package com.example.sportsapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsapp.adapter.EventAdapter
import com.example.sportsapp.databinding.ActivityEventListBinding
import com.example.sportsapp.main.MainApp

class EventListActivity : AppCompatActivity(){
    lateinit var app: MainApp
    private lateinit var binding: ActivityEventListBinding

    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewEvents.layoutManager = layoutManager
        binding.recyclerViewEvents.adapter = EventAdapter(app.events)

        val fab = binding.fabEvents
        fab.setOnClickListener {
            val launcherIntent = Intent(this, EventActivity::class.java)
            refreshIntentLauncher.launch(launcherIntent)
        }

        registerRefreshCallback()
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            binding.recyclerViewEvents.adapter?.notifyDataSetChanged()
        }
    }
}