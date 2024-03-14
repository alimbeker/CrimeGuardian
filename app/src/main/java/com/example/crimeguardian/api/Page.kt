package com.example.crimeguardian.api

import com.google.gson.annotations.SerializedName

data class Page(
    @SerializedName("PageTitle")
    val title: String,
    @SerializedName("PageUrl")
    val url: String,
    @SerializedName("Count")
    val count: Int,
    @SerializedName("CountPercent")
    val countPercent: Double,
    @SerializedName("Diff")
    val diff: Int,
    @SerializedName("DiffPercent")
    val diffPercent: Double,
    @SerializedName("Read_end")
    val readEnd: Int,
    @SerializedName("Moved_on")
    val movedOn: Int,
    @SerializedName("Read")
    val read: Read
)

data class Read(
    @SerializedName("Minute")
    val minute: Int,
    @SerializedName("Second")
    val second: Int
)

