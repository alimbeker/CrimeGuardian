package com.example.crimeguardian.presentation.incidents.fragment

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.crimeguardian.R
import com.example.crimeguardian.core.BaseFragment
import com.example.crimeguardian.databinding.FragmentIncidentsBinding
import com.example.crimeguardian.presentation.incidents.fragment.cluster.ClusterRenderer
import com.example.crimeguardian.presentation.incidents.fragment.cluster.MyClusterItem
import com.example.crimeguardian.presentation.incidents.fragment.viewmodel.IncidentsViewModel
import com.example.crimeguardian.core.PermissionManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.NonHierarchicalViewBasedAlgorithm
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class IncidentsFragment : BaseFragment<FragmentIncidentsBinding>(FragmentIncidentsBinding::inflate),
    OnMapReadyCallback {

    private val viewModel: IncidentsViewModel by viewModels()
    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private lateinit var clusterManager: ClusterManager<MyClusterItem>
    private lateinit var clusterRenderer: ClusterRenderer<MyClusterItem>
    private var selectedLocationMarker: Marker? = null
    private lateinit var notificationHelper: NotificationHelper
    private lateinit var alertDialogHelper: AlertDialogHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(savedInstanceState)
        observeViewModel()
        initializeHelpers()
        loadAndParseGeoJsonData()
    }

    private fun initializeHelpers() {
        notificationHelper = NotificationHelper(this)
        alertDialogHelper = AlertDialogHelper(requireContext())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val style = MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style)
        mMap.setMapStyle(style)

        mMap.setOnMapClickListener { latLng ->
            showSelectedLocationMarker(latLng)
            observeLocation(latLng)
        }

        initializeClusterManager()
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

    private fun showSelectedLocationMarker(latLng: LatLng) {
        // Remove previous marker if exists
        selectedLocationMarker?.remove()
        // Add new marker for selected location
        selectedLocationMarker = mMap.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
        selectedLocationMarker?.showInfoWindow() // Show info window above marker
    }

    private fun observeLocation(location: LatLng) {
        if (!PermissionManager.checkPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            PermissionManager.requestLocationPermission(this)
            return
        }

        viewModel.countMarkersWithinCircle(center = location, lifecycleOwner = viewLifecycleOwner)

        viewModel.resultLiveData.observe(viewLifecycleOwner) { count ->
            alertDialogHelper.showMarkerCountAlert(count)

            if (count > 400) {
                notificationHelper.showNotification("Notification", "Be careful! You are in a dangerous area")
            }
            Log.d("DangerLevel", "Count: $count")
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


