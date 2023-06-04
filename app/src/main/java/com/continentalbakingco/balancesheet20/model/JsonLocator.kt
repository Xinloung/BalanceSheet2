package com.continentalbakingco.balancesheet20.model

import com.google.gson.annotations.SerializedName

data class JsonLocator(
    @SerializedName("countryId")
    val CountryId: Int,
    @SerializedName("id")
    val Id: Int,
    @SerializedName("locationCode")
    val LocationCode: String,
    @SerializedName("locationDescription")
    val LocationDescription: String
)