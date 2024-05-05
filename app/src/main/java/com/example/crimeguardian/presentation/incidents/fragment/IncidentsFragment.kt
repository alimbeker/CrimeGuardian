package com.example.crimeguardian.presentation.incidents.fragment

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.example.crimeguardian.R
import com.example.crimeguardian.core.BaseFragment
import com.example.crimeguardian.databinding.FragmentIncidentsBinding
import com.example.crimeguardian.presentation.incidents.fragment.cluster.ClusterRenderer
import com.example.crimeguardian.presentation.incidents.fragment.cluster.MyClusterItem
import com.example.crimeguardian.presentation.incidents.fragment.viewmodel.IncidentsViewModel
import com.example.crimeguardian.presentation.profile.fragment.PermissionManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.NonHierarchicalViewBasedAlgorithm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IncidentsFragment : BaseFragment<FragmentIncidentsBinding>(FragmentIncidentsBinding::inflate),
    OnMapReadyCallback {
    private val viewModel: IncidentsViewModel by viewModels()
    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private lateinit var clusterManager: ClusterManager<MyClusterItem>
    private lateinit var clusterRenderer: ClusterRenderer<MyClusterItem>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(savedInstanceState)
        observeViewModel()
        observeLocation()
        loadAndParseGeoJsonData()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //style
        val style = MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style)
        mMap.setMapStyle(style)

        initializeClusterManager()
        setupMapSettings()
        zoomToDefaultLocation()
    }

    private fun initializeViews(savedInstanceState: Bundle?) {
        // Initialize the MapView
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
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

    private fun observeLocation() {
        if (PermissionManager.checkPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            viewModel.countMarkersWithinCircle(
                lifecycleOwner = viewLifecycleOwner, center = LatLng(43.069835, 76.731822)
            )
            viewModel.resultLiveData.observe(viewLifecycleOwner) { count ->
                if (count > 400) {
                    showDangerAlert()
                }
                Log.d("DangerLevel", "Count: $count")
            }
        } else {
            // Request location permission
            PermissionManager.requestLocationPermission(this)
        }
    }
    private fun showDangerAlert() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle("Danger Alert")
            setMessage("You are in dangerous area. Leave it!")
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
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
