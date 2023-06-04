package com.continentalbakingco.balancesheet20.model

import com.google.gson.annotations.SerializedName

data class User(
         @SerializedName("Created_At")
         val Created_At: String?,
         @SerializedName("Deleted_At")
         val Deleted_At: String?,
         @SerializedName("Email")
         val Email: String,
         @SerializedName("FirstName")
         val FirstName: String,
         @SerializedName("LastName")
         val LastName: String,
         @SerializedName("Password")
         val Password: String,
         @SerializedName("RoleId")
         val RoleId: Int?,
         @SerializedName("RouteId")
         val RouteId: Int,
         @SerializedName("Status")
         val Status: Boolean?,
         @SerializedName("TRN")
         val TRN: String,
         @SerializedName("Updated_At")
         val Updated_At: String?,
         @SerializedName("Username")
         val Username: String?
                 )