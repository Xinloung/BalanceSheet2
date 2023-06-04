package com.continentalbakingco.balancesheet20.`interface`

import android.content.Context
import com.continentalbakingco.balancesheet20.model.JsonUser
import retrofit2.Response

interface Auth {

 suspend fun CheckEmail( email : String) : Response<JsonUser>?



}