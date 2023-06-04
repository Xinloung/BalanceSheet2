package com.continentalbakingco.balancesheet20.model

import com.google.gson.annotations.SerializedName

data class JsonCountry(
    @SerializedName("country")
    val Country: String,
    @SerializedName("countryCode")
    val CountryCode: String,
    @SerializedName("id")
    val Id: Int
)