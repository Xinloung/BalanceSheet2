package com.continentalbakingco.balancesheet20.model

import com.google.gson.annotations.SerializedName

data class JSONRoute(
    @SerializedName("isActive")
    val IsActive: Boolean,
    @SerializedName("routeDescription")
    val RouteDescription: String,
    @SerializedName("routeID")
    val RouteID: Int,
    @SerializedName("routeNum")
    val RouteNum: String,
    @SerializedName("routeTypeID")
    val RouteTypeID: Int
)
