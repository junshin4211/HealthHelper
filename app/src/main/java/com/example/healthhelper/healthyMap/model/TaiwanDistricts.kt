package com.example.ihealth.model

import com.google.gson.annotations.SerializedName

data class District(
    @SerializedName("name") val name: String
)

data class City(
    @SerializedName("districts") val districts: List<District>,
    @SerializedName("name") val name: String
)
