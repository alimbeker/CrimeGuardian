package com.example.crimeguardian.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crimeguardian.presentation.cluster.manager.MyClusterItem
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
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
            val items = mutableListOf<MyClusterItem>()
            val geoJsonData = Gson().fromJson(geoJsonString, GeoJsonData::class.java)

            geoJsonData.features.forEach { feature ->
                val geometry = feature.geometry
                val coordinates = geometry.coordinates
                val latLng = LatLng(coordinates[1], coordinates[0])
                val properties = feature.properties

                val crimeCode = properties.crime_code
                if (crimeCode != null) {
                    val myItem = MyClusterItem(
                        latLng,
                        crimeCode
                    )
                    items.add(myItem)
                } else {
                    // Handle null crimeCode, if needed
                }
            }
            items
        }
    }
}

data class GeoJsonData(
    val type: String,
    val features: List<Feature>
)

data class Feature(
    val type: String,
    val geometry: Geometry,
    val properties: Properties
)

data class Geometry(
    val type: String,
    val coordinates: List<Double>
)

data class Properties(
    val dat_sover: String?,
    val street: String?,
    val stat: String?,
    val time_period: String?,
    val organ: String?,
    val crime_code: String?,
    val hard_code: String?,
    val month: String?,
    val year: String?,
    val city_code: String?,
    val ud: String?,
    val longitude: String?,
    val latitude: String?,
    val objectid: String?,
    val home_number: String?,
    val reg_code: String?
)




