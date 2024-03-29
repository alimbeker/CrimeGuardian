package com.example.crimeguardian.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.crimeguardian.databinding.FragmentIncidentsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIncidentsBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the SearchView
        searchView = binding.searchView

        // Initialize the MapView
        mapView = binding.mapView

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Set up search functionality
        addSearchView()

        // Set up current location button
        val currentLocationButton = binding.currentLoc
        currentLocationButton.setOnClickListener {
            fetchCurrentLocation()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

    }

    private fun addSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(requireContext(), "Search: $query", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker for Almaty, Kazakhstan
        val almatyLatLng = LatLng(43.2551, 76.9126)
        val marker = MarkerOptions().position(almatyLatLng).title("Almaty City")
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

    private fun fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permissions
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            return
        }

        // Fetch the last known location
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    // Move the camera to the current location
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                } ?: run {
                    Toast.makeText(
                        requireContext(),
                        "Failed to retrieve current location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}
