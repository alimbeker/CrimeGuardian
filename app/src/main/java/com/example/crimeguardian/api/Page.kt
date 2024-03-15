package com.example.crimeguardian.api

import com.google.gson.annotations.SerializedName


data class Page(
    @SerializedName("PageTitle")
    val title: String? = null,
    @SerializedName("PageUrl")
    val url: String? = null,
    @SerializedName("Count")
    val count: Int? = null,
    @SerializedName("CountPercent")
    val countPercent: Double? = null,
    @SerializedName("Diff")
    val diff: Int? = null,
    @SerializedName("DiffPercent")
    val diffPercent: Double? = null,
    @SerializedName("Read_end")
    val readEnd: Int? = null,
    @SerializedName("Moved_on")
    val movedOn: Int? = null,
    @SerializedName("Read")
    val read: Read? = null
)

data class Read(
    @SerializedName("Minute")
    val minute: Int? = null,
    @SerializedName("Second")
    val second: Int? = null
)

