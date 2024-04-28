package com.example.crimeguardian.presentation.incidents.fragment

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.crimeguardian.R
import com.example.crimeguardian.core.BaseFragment
import com.example.crimeguardian.databinding.FragmentIncidentsBinding
import com.example.crimeguardian.presentation.incidents.fragment.cluster.ClusterRenderer
import com.example.crimeguardian.presentation.incidents.fragment.cluster.MyClusterItem
import com.example.crimeguardian.presentation.incidents.fragment.viewmodel.IncidentsViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.NonHierarchicalViewBasedAlgorithm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IncidentsFragment : BaseFragment<FragmentIncidentsBinding>(FragmentIncidentsBinding::inflate),
    OnMapReadyCallback {

    private lateinit var searchView: SearchView
    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private lateinit var clusterManager: ClusterManager<MyClusterItem>
    private lateinit var clusterRenderer: ClusterRenderer<MyClusterItem>
    private lateinit var viewModel: IncidentsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(savedInstanceState)
        initializeViewModel()
        setupSearchFunctionality()
        observeViewModel()
        loadAndParseGeoJsonData()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        initializeClusterManager()
        setupMapSettings()
        zoomToDefaultLocation()
    }

    private fun initializeViews(savedInstanceState: Bundle?) {
        // Initialize the SearchView
        searchView = binding.searchView

        // Initialize the MapView
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    private fun initializeViewModel() {
        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[IncidentsViewModel::class.java]
    }

    private fun setupSearchFunctionality() {
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
    }

    private fun observeViewModel() {
        // Observe ViewModel LiveData
        viewModel.geoJsonData.observe(viewLifecycleOwner) { items ->
            // Update map with loaded and parsed data
            items?.let {
                clusterManager.addItems(it)
                clusterManager.cluster()
            }
        }
    }

    private fun loadAndParseGeoJsonData() {
        // Load and parse GeoJSON data
        viewModel.loadAndParseGeoJson(requireContext(), getString(R.string.response_geojson))
    }


    private fun initializeClusterManager() {
        // Initialize the ClusterManager
        clusterManager = ClusterManager(requireContext(), mMap)
        clusterRenderer = ClusterRenderer(requireContext(), mMap, clusterManager)
        clusterManager.renderer = clusterRenderer
        mMap.setOnCameraIdleListener(clusterManager)

        // Use NonHierarchicalViewBasedAlgorithm
        val displayMetrics = requireContext().resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        clusterManager.algorithm = NonHierarchicalViewBasedAlgorithm(screenWidth, screenHeight)
    }

    private fun setupMapSettings() {
        // Additional map settings can be applied here
        // For example:
        // mMap.uiSettings.isZoomControlsEnabled = true
        // mMap.uiSettings.isMyLocationButtonEnabled = true
    }

    private fun zoomToDefaultLocation() {
        // Zoom to a default location
        val defaultLocation = LatLng(43.2551, 76.9126)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
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
