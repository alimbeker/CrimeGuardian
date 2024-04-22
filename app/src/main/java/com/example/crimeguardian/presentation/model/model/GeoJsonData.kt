package com.example.crimeguardian.presentation.model.model

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




