package com.continentalbakingco.balancesheet20.`interface`

import com.continentalbakingco.balancesheet20.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.net.ResponseCache

interface ApiRequest {
    @GET("api/user/{email}")
    fun getAllUser(@Path("email") Email : String) : Call<JsonUser>
    @GET("api/user")
    fun getAllUser() : Call<List<JsonUser>>
    @GET("api/routenumber")
    fun getRoutes() : Call<List<JSONRoute>>
    @GET("api/routenumber/{routenumber}")
    fun getRoute(@Path("routenumber") routenumber : String) : Call<JSONRoute>
    @GET("api/routenumber/myroute/{id}")
    fun getMyRoute(@Path("id") id : Int) : Call<JSONRoute>
    @POST("api/user")
    fun createUser(@Body user : JsonUser) : Call<JsonUser>
    @GET("api/balancesheet/mysheet/{uid}")
    fun getMySheets(@Path("uid") uid : Int) : Call<List<BalanceSheet>>
    @GET("api/routetype/{id}")
    fun getRouteTypes(@Path("id") id: Int) : Call<JSONRouteType>
    @GET("api/routetype")
    fun getRouteTypes() : Call<List<JSONRouteType>>
    @GET("api/accounttype")
    fun getAccountType() : Call<List<JSONAccountType>>
    @GET("api/location/{id}")
    fun getLocation(@Path("id") id: Int) : Call<JsonLocation>
    @GET("api/location/{locationCode}")
    fun getLocation(@Path("locationCode") locationCode: String) : Call<JsonLocation>
    @GET("api/location")
    fun getLocations() : Call<List<JsonLocation>>
    @GET("api/locator")
    fun getLocators() : Call<List<JsonLocator>>
    @GET("api/locator/{id}")
    fun getLocators(@Path("id") id :Int) : Call<JsonLocator>
    @GET("api/locator/Location/{id}")
    fun getLocatorCountry(@Path("id") id :Int) : Call<List<JsonLocator>>
    @GET("api/Country")
    fun getCountries() : Call<List<JsonCountry>>
    @GET("api/Country/{id}")
    fun getCountry(@Path("id") id :Int) : Call<JsonCountry>
}