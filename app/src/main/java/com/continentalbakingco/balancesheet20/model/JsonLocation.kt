package com.continentalbakingco.balancesheet20.model

import com.google.gson.annotations.SerializedName

data class JsonLocation(
    @SerializedName("country")
    val Country: String,
    @SerializedName("countryCode")
    val CountryCode: String,
    @SerializedName("id")
    val Id: Int,
    @SerializedName("locator")
    val Locator: String,
    @SerializedName("locatorCode")
    val LocatorCode: String
)