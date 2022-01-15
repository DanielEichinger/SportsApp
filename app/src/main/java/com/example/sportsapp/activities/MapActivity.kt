package com.example.sportsapp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sportsapp.R
import timber.log.Timber.i

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.sportsapp.databinding.ActivityMapBinding
import com.example.sportsapp.main.MainApp
import com.example.sportsapp.models.GpsLocation
import com.example.sportsapp.models.Location
import com.google.android.gms.maps.model.Marker

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    lateinit var app: MainApp

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapBinding
    var location = GpsLocation()
    var location_list = ArrayList<Location>()
    private lateinit var mode: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("location_edit")) {
            mode = "edit"
            location = intent.extras?.getParcelable<GpsLocation>("location_edit")!!
        }
        if (intent.hasExtra("location_display")) {
            mode = "display"
            location = intent.extras?.getParcelable<GpsLocation>("location_display")!!
        }
        if (intent.hasExtra("location_list")) {
            mode = "display_list"
            app.locations.getAll().forEach {
                location_list.add(it)
            }
            location_list.forEach { i("Location: $it") }
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        if (mode == "edit") {
            resultIntent.putExtra("location_edit", location)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        super.onBackPressed()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (mode == "edit") {
            map = googleMap
            map.setOnMarkerDragListener(this)
            val loc = LatLng(location.lat, location.lng)
            val options = MarkerOptions()
                .draggable(true)
                .position(loc)
            map.addMarker(options)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
        }
        if (mode == "display") {
            map = googleMap
            val loc = LatLng(location.lat, location.lng)
            val options = MarkerOptions()
                .position(loc)
            map.addMarker(options)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
        }
        if (mode == "display_list") {
            map = googleMap
            location_list.forEach {
                val loc = LatLng(it.GpsLoc.lat, it.GpsLoc.lng)
                val options = MarkerOptions()
                    .position(loc)
                    .title(it.name)
                    .snippet(it.description)
                map.addMarker(options)
            }
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(49.010830, 12.099704), 12f))
        }
    }

    override fun onMarkerDragStart(marker: Marker) {
    }

    override fun onMarkerDrag(marker: Marker) {
    }

    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = map.cameraPosition.zoom
    }
}