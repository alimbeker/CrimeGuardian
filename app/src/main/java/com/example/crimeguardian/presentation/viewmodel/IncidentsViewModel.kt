package com.example.crimeguardian.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crimeguardian.presentation.cluster.manager.MyClusterItem
import com.google.android.gms.maps.model.LatLng
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
            val featureCollection = JSONObject(geoJsonString)
            val features = featureCollection.getJSONArray("features")

            for (i in 0 until features.length()) {
                val feature = features.getJSONObject(i)
                val geometry = feature.getJSONObject("geometry")
                val coordinates = geometry.getJSONArray("coordinates")
                val latLng = LatLng(coordinates.getDouble(1), coordinates.getDouble(0))
                val properties = feature.getJSONObject("properties")

                val myItem = MyClusterItem(
                    latLng,
                    properties.getString("crime_code")
                )

                items.add(myItem)
            }
            items
        }
    }
}
