package com.example.crimeguardian.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crimeguardian.presentation.model.model.incidents.GeoJsonData
import com.example.crimeguardian.presentation.incidents.fragment.cluster.MyClusterItem
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class IncidentsViewModel : ViewModel() {

    private val _geoJsonData = MutableLiveData<List<MyClusterItem>>()
    val geoJsonData: LiveData<List<MyClusterItem>> = _geoJsonData

    fun loadAndParseGeoJson(context: Context, fileName: String) {
        viewModelScope.launch {
            try {
                val geoJsonString = readGeoJsonFromAssets(context, fileName)
                val parsedData = parseGeoJson(geoJsonString)
                _geoJsonData.postValue(parsedData)
            } catch (e: IOException) {
                // Handle error
                e.printStackTrace()
            }
        }
    }

    private suspend fun readGeoJsonFromAssets(context: Context, fileName: String): String {
        return withContext(Dispatchers.IO) {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        }
    }

    private suspend fun parseGeoJson(geoJsonString: String): List<MyClusterItem> {
        return withContext(Dispatchers.IO) {
            // Step 1: Input Parsing
            val geoJsonData = parseGeoJsonData(geoJsonString)

            // Step 2: Data Processing
            val items = processGeoJsonData(geoJsonData)

            // Step 3: Item Creation
            val clusterItems = createClusterItems(items)

            clusterItems
        }
    }

    private fun parseGeoJsonData(geoJsonString: String): GeoJsonData {
        return Gson().fromJson(geoJsonString, GeoJsonData::class.java)
    }

    private fun processGeoJsonData(geoJsonData: GeoJsonData): List<Pair<LatLng, String>> {
        val items = mutableListOf<Pair<LatLng, String>>()

        geoJsonData.features.forEach { feature ->
            val geometry = feature.geometry
            val coordinates = geometry.coordinates
            val latLng = LatLng(coordinates[1], coordinates[0])
            val properties = feature.properties

            val crimeCode = properties.crime_code

            if (crimeCode != null) {
                items.add(Pair(latLng, crimeCode))
            }
        }

        return items
    }

    private fun createClusterItems(items: List<Pair<LatLng, String>>): List<MyClusterItem> {
        return items.map { (latLng, crimeCode) ->
            MyClusterItem(latLng, crimeCode)
        }
    }

}

