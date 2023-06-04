package com.continentalbakingco.balancesheet20.model

import com.google.gson.annotations.SerializedName

data class JSONAccountType(
    @SerializedName("description")
    val Description: String,
    @SerializedName("id")
    val Id: Int,
    @SerializedName("isActive")
    val IsActive: Boolean
)