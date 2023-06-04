package com.continentalbakingco.balancesheet20.model

import com.google.gson.annotations.SerializedName

data class JsonUser(
        @SerializedName("created_at")
        val Created_At: String,
        @SerializedName("deleted_at")
        val Deleted_At: String?,
        @SerializedName("email")
        val Email: String,
        @SerializedName("firstName")
        val FirstName: String,
        @SerializedName("id")
        val Id: Int,
        @SerializedName("lastName")
        val LastName: String,
        @SerializedName("password")
        val Password: String,
        @SerializedName("roleid")
        val RoleId: Int?,
        @SerializedName("routeid")
        val RouteId: Int,
        @SerializedName("status")
        val Status: Boolean?,
        @SerializedName("trn")
        val TRN: String,
        @SerializedName("updated_at")
        val Updated_At: String,
        @SerializedName("username")
        val Username: String?,
        @SerializedName("locatorCode")
        val LocatorCode: String,
        @SerializedName("countryCode")
        val CountryCode: String
)
