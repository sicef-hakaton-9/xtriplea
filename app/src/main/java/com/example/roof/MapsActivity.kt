package com.example.roof

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.roof.databinding.ActivityMapsBinding
import com.example.roof.models.Building
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.tasks.Task
import java.util.Dictionary


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var loc: LatLng

    private lateinit var mFusedLocationClient: FusedLocationProviderClient


    private val dictionary = mutableMapOf<Marker, Building>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.floatButton.setOnClickListener{
            val i = Intent(this, BuldingSpecs::class.java)
            i.putExtra("lat", loc.latitude)
            i.putExtra("lng", loc.longitude)
            startActivity(i)

        }

        checkLocationPermission()
    }

    private fun checkLocationPermission(){

        val task: Task<Location> = mFusedLocationClient.lastLocation

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return;
        }

        task.addOnSuccessListener {
            if(it!=null){
               // Toast.makeText(this, "${it.latitude}, ${it.longitude}", Toast.LENGTH_LONG).show()
                loc = LatLng(it.latitude, it.longitude)
                //setMarker(loc)
            }
            else{
                Toast.makeText(this, "NECE", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val instance = Singleton
        instance.app.load(this)
        for(b in instance.app.buildings){
            var marker = setMarker(LatLng(b.lat, b.lng))
            dictionary[marker] = b
        }
        mMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {

        val intent = Intent(this, CurrentScore::class.java)
        //Toast.makeText(this, dictionary[marker].toString(), Toast.LENGTH_LONG).show()
        intent.putExtra("bld", dictionary[marker])
        startActivity(intent)
        return true
    }

    private fun setMarker(loc: LatLng) : Marker {
        var marker = mMap.addMarker(MarkerOptions().position(loc).title("Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 12F))
        return marker!!
    }
}

