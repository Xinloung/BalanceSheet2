package com.continentalbakingco.balancesheet20.model

import com.google.gson.annotations.SerializedName

data class JSONRouteType(
    @SerializedName("routeTypeDescription")
    val RouteTypeDescription: String,
    @SerializedName("routeTypeID")
    val RouteTypeID: Int
)