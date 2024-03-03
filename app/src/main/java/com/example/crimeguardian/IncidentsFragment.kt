package com.example.crimeguardian

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import com.example.crimeguardian.databinding.FragmentIncidentsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class IncidentsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentIncidentsBinding
    private lateinit var searchView: SearchView
    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIncidentsBinding.inflate(inflater, container, false)


        searchView = binding.searchView

        // Initialize the MapView
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Set up search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(requireContext(), "Search: $query", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker for Almaty, Kazakhstan
        val almatyLatLng = LatLng(43.2551, 76.9126)
        val marker = MarkerOptions()
            .position(almatyLatLng)
            .title("Almaty City")
            .snippet("Welcome to Almaty!")

        map.addMarker(marker)

        // Move the camera to Almaty
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(almatyLatLng, 12f))
    }


    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}