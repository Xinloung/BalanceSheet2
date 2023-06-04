package com.continentalbakingco.balancesheet20.repository

import com.continentalbakingco.balancesheet20.`interface`.ApiRequest
import com.continentalbakingco.balancesheet20.`interface`.ApiUtil
import com.continentalbakingco.balancesheet20.constants.Constants.BASE_URL
import com.continentalbakingco.balancesheet20.model.*
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

open class UtilManager : ApiUtil {
    private val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

    override suspend fun utilResponseUser(email: String): Call<JsonUser> {
        return retrofitBuilder.getAllUser(email)
    }

    override suspend fun utilResponseUser(): Call<List<JsonUser>> {
        return retrofitBuilder.getAllUser()
    }

    override suspend fun utilResponseRoute(routeNumber: String): Call<JSONRoute> {
       return retrofitBuilder.getRoute(routeNumber)
    }

    override suspend fun utilResponseRoute(): Call<List<JSONRoute>> {
        return retrofitBuilder.getRoutes()
    }

    override suspend fun utilResponseRouteType(id: Int): Call<JSONRouteType> {
        return retrofitBuilder.getRouteTypes(id)
    }

    override suspend fun utilResponseAccountType(): Call<List<JSONAccountType>> {
        return retrofitBuilder.getAccountType()
    }

    override suspend fun utilResponseLocation(id: Int): Call<JsonLocation> {
       return retrofitBuilder.getLocation(id)
    }

    override suspend fun utilResponseLocation(): Call<List<JsonLocation>> {
        return retrofitBuilder.getLocations()
    }

    override suspend fun utilResponseLocation(countryCode: String): Call<JsonLocation> {
        return retrofitBuilder.getLocation(countryCode)
    }

    override suspend fun utilResponseLocator(): Call<List<JsonLocator>> {
        return retrofitBuilder.getLocators()
    }

    override suspend fun utilResponseLocator(id: Int): Call<JsonLocator> {
        return retrofitBuilder.getLocators(id)
    }

    override suspend fun utilResponseCountry(id: Int): Call<JsonCountry> {
        return retrofitBuilder.getCountry(id)
    }

    override suspend fun utilResponseCountry(): Call<List<JsonCountry>> {
        return retrofitBuilder.getCountries()

    }

    override suspend fun utilResponseCountryList(id: Int): Call<List<JsonLocator>> {
        return retrofitBuilder.getLocatorCountry(id)
    }

    override suspend fun utilResponseCreateUser(user: JsonUser): Call<JsonUser> {
        return retrofitBuilder.createUser(user)
    }

    override suspend fun utilResponseGetMySheets(id: Int): Call<List<BalanceSheet>> {
        return retrofitBuilder.getMySheets(id)
    }

    override suspend fun utilResponseGetMyRouteId(id: Int): Response<JSONRoute> {
        return retrofitBuilder.getMyRoute(id).awaitResponse()
    }

    override suspend fun utilResponseRouteType(): Call<List<JSONRouteType>> {
      return retrofitBuilder.getRouteTypes()
    }
}

