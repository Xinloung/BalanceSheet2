package com.continentalbakingco.balancesheet20.viewmodel

import android.util.Base64
import com.continentalbakingco.balancesheet20.`interface`.Auth
import com.continentalbakingco.balancesheet20.model.JsonUser
import com.continentalbakingco.balancesheet20.repository.UtilManager
import com.toxicbakery.bcrypt.Bcrypt
import retrofit2.Response
import retrofit2.awaitResponse

class Authenticator : Auth {

    //Validate email
    override suspend fun CheckEmail(email: String) : Response<JsonUser> {
     val util : UtilManager = UtilManager()
     return util.utilResponseUser(email).awaitResponse()
 }

    //Check if Record Already Exits
    suspend fun checkCredentials(email : String, trn : String) : Boolean{
        val util : UtilManager = UtilManager()
        var response : Response<JsonUser> = util.utilResponseUser(email).awaitResponse()

      return if(response.isSuccessful){
          response.body()?.Email == email || response.body()?.TRN == trn
      }else{
          false
      }
    }

    //Encryption
    fun encrypt(password: String) : String {
  val saltRounds : Int = 12
  val hash = Bcrypt.hash(password, saltRounds )
  return Base64.encodeToString(hash, Base64.DEFAULT)

 }

    //Decryption
    fun decrypt(encrypted: ByteArray, password: String) : Boolean {
  return Bcrypt.verify(password, Base64.decode(encrypted, Base64.DEFAULT))
 }
}