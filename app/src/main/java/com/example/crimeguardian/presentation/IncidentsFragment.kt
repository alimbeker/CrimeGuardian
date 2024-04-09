package com.example.crimeguardian.presentation

import android.content.Context
import android.content.res.AssetManager
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import com.example.crimeguardian.core.BaseFragment
import com.example.crimeguardian.databinding.FragmentIncidentsBinding
import com.example.crimeguardian.presentation.cluster.manager.MyClusterItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.ClusterRenderer
import org.json.JSONObject

class IncidentsFragment : BaseFragment<FragmentIncidentsBinding>(FragmentIncidentsBinding::inflate), OnMapReadyCallback {

    private lateinit var searchView: SearchView
    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private lateinit var clusterManager: ClusterManager<ClusterItem>
    private lateinit var clusterRenderer: ClusterRenderer<ClusterItem>

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
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Initialize the ClusterManager
        clusterManager = ClusterManager(requireContext(), mMap)
        clusterRenderer = com.example.crimeguardian.presentation.cluster.manager.ClusterRenderer(
            requireContext(),
            mMap,
            clusterManager
        )
        clusterManager.renderer = clusterRenderer
        mMap.setOnCameraIdleListener(clusterManager)

        // Read GeoJSON data from assets
        val geoJsonString = readGeoJsonFromAssets(requireContext(), "response.geojson")
        parseGeoJson(geoJsonString)

        // Zoom to a default location
        val defaultLocation = LatLng(43.2551, 76.9126)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
    }

    private fun readGeoJsonFromAssets(context: Context, fileName: String): String {
        val assetManager: AssetManager = context.assets
        return assetManager.open(fileName).bufferedReader().use { it.readText() }
    }

    private fun parseGeoJson(geoJsonString: String) {
        val featureCollection = JSONObject(geoJsonString)
        val features = featureCollection.getJSONArray("features")

        for (i in 0 until features.length()) {
            val feature = features.getJSONObject(i)
            val geometry = feature.getJSONObject("geometry")
            val coordinates = geometry.getJSONArray("coordinates")
            val latLng = LatLng(coordinates.getDouble(1), coordinates.getDouble(0))
            val properties = feature.getJSONObject("properties")

            // Add item to the cluster manager
            val myItem = MyClusterItem(
                latLng,
                properties.getString("crime_code")
            )
            clusterManager.addItem(myItem)
        }

        // Update clusters
        clusterManager.cluster()
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





