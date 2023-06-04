package com.continentalbakingco.balancesheet20.`interface`

import com.continentalbakingco.balancesheet20.model.*
import retrofit2.Call
import retrofit2.Response

interface ApiUtil {
   suspend fun utilResponseUser(email : String) : Call<JsonUser>
   suspend fun utilResponseUser() : Call<List<JsonUser>>
   suspend fun utilResponseRoute(routeNumber : String) : Call<JSONRoute>
   suspend fun utilResponseRoute() : Call<List<JSONRoute>>
   suspend fun utilResponseCreateUser(user: JsonUser) : Call<JsonUser>
   suspend fun utilResponseGetMySheets(id: Int) : Call<List<BalanceSheet>>
   suspend fun utilResponseGetMyRouteId(id : Int) : Response<JSONRoute>
   suspend fun utilResponseRouteType() : Call<List<JSONRouteType>>
   suspend fun utilResponseRouteType(id : Int) : Call<JSONRouteType>
   suspend fun utilResponseAccountType() : Call<List<JSONAccountType>>
   suspend fun utilResponseLocation(id : Int) : Call<JsonLocation>
   suspend fun utilResponseLocation() : Call<List<JsonLocation>>
   suspend fun utilResponseLocation(countryCode: String) : Call<JsonLocation>
   suspend fun utilResponseLocator() : Call<List<JsonLocator>>
   suspend fun utilResponseLocator(id : Int)  : Call<JsonLocator>
   suspend fun utilResponseCountry(id : Int)  : Call<JsonCountry>
   suspend fun utilResponseCountry()  : Call<List<JsonCountry>>
   suspend fun utilResponseCountryList(id : Int)  : Call<List<JsonLocator>>



}