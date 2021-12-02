package com.example.sportsapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsapp.adapter.EventAdapter
import com.example.sportsapp.databinding.ActivityEventListBinding
import com.example.sportsapp.main.MainApp

class EventListActivity : AppCompatActivity(){
    lateinit var app: MainApp
    private lateinit var binding: ActivityEventListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewEvents.layoutManager = layoutManager
        binding.recyclerViewEvents.adapter = EventAdapter(app.events)

        val fab = binding.fabEvents
        fab.setOnClickListener { view ->
            val launcherIntent = Intent(this, EventActivity::class.java)
            startActivityForResult(launcherIntent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerViewEvents.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}