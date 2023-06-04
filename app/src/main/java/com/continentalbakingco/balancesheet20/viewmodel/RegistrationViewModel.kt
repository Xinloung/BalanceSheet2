package com.continentalbakingco.balancesheet20.viewmodel

import android.content.Intent
import android.os.Handler
import android.text.TextUtils
import android.view.View
import com.continentalbakingco.balancesheet20.R
import com.continentalbakingco.balancesheet20.databinding.ActivityRegistrationBinding
import com.continentalbakingco.balancesheet20.model.JsonUser
import com.continentalbakingco.balancesheet20.repository.UtilManager
import com.continentalbakingco.balancesheet20.view.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class RegistrationViewModel : BaseActivity() {
     lateinit var bind : ActivityRegistrationBinding

     //Checks User Input
    suspend  fun checkInput():Boolean{
          return  when{
               TextUtils.isEmpty(bind.etFirstName.text.toString().trim{it <=' '})->{
                    showSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                    false
               }
               TextUtils.isEmpty(bind.etLastName.text.toString().trim{it <=' '})->{
                    showSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                    false
               }
               TextUtils.isEmpty(bind.etTrn.text.toString().trim{it <=' '})->{
                    showSnackBar(resources.getString(R.string.err_msg_enter_trn), true)
                    false
               }
               bind.etTrn.text?.count()!! < 9 ->{
                    showSnackBar(resources.getString(R.string.err_msg_invalid_trn), true)
                    false
               }
               TextUtils.isEmpty(bind.etEmailId.text.toString().trim{it <=' '})->{
                    showSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                    false
               }
               !isValidEmail(bind.etEmailId.text.toString().trim{it <=' '}) ->{
                    showSnackBar(resources.getString(R.string.err_msg_invalid_email_address), true)
                    false
               }
               TextUtils.isEmpty(bind.etPassword.text.toString().trim{it <=' '})->{
                    showSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                    false
               }
               TextUtils.isEmpty(bind.etConfirmPassword.text.toString().trim{it <=' '})->{
                    showSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                    false
               }
               bind.etPassword.text.toString().trim{it <=' '} != bind.etConfirmPassword.text.toString().trim{it <=' '}->{
                    showSnackBar(resources.getString(R.string.err_msg_confirm_password_mismatch), true)
                    false
               }
               !bind.cbTermsAndConditions.isChecked ->{
                    showSnackBar(resources.getString(R.string.err_msg_agree_terms_and_conditions), true)
                    false
               }
               Authenticator().checkCredentials(bind.etEmailId.text.toString(), bind.etTrn.text.toString())->{
                    showSnackBar(getString(R.string.err_msg_trn_or_email_already_registered), true)
                    false
               }
               else-> true
          }
     }

     //Creates New User
      fun createUser(){
         bind.pbRegistration.visibility = View.VISIBLE
         val auth : Authenticator = Authenticator()
          val util : UtilManager = UtilManager()
          bind.btnRegister.isEnabled= false

          var date : DateTime = DateTime.now()
          currentDateTime = date.toString()
         getLocatorCode(bind.spLocationId.selectedItemPosition+1, bind.spLocatorId)
         getRouteID(bind.spRouteId.selectedItem.toString())

          try{
               GlobalScope.launch (Dispatchers.IO) {
                   val user : JsonUser = JsonUser(
                           Created_At = currentDateTime,
                           Deleted_At = null,
                           Email = bind.etEmailId.text.toString(),
                           FirstName = bind.etFirstName.text.toString(),
                           LastName =  bind.etLastName.text.toString(),
                           Id = 0,
                           Password = auth.encrypt(bind.etPassword.text.toString()),
                           RoleId =  null,
                           RouteId =  routeID,
                           Status =  true,
                           TRN =  bind.etTrn.text.toString(),
                           Updated_At =  currentDateTime,
                           Username = null,
                           LocatorCode = locatorCode.trim { it <=' ' },
                           CountryCode = countryCode.trim { it <=' ' }
                   )
                    val callBody: Call<JsonUser> = util.utilResponseCreateUser(user)

                    callBody.enqueue(object : Callback<JsonUser> {
                         override fun onResponse(call: Call<JsonUser>, response: Response<JsonUser>) {
                              val code: Int = response.code()
                              val handler: Handler = Handler()
                              val message: String = response.message()
                              if (code !=201) {
                                   showSnackBar("$code: User Creation Failed!", true)
                                   println(message + locatorCode + countryCode)
                                  return
                              } else {
                                   showSnackBar("Success, User Account Created!", false)
                                   handler.postDelayed({
                                        val intent: Intent = Intent(this@RegistrationViewModel, LoginActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                   }, 1000)
                              }
                         }
                         override fun onFailure(call: Call<JsonUser>, t: Throwable) {
                              showSnackBar(resources.getString(R.string.err_msg_check_internet), true)
                         }

                    })
               }
          }catch (e : Exception)
          {
               println(e.message);
          }finally {
               bind.btnRegister.isEnabled= true
               bind.pbRegistration.visibility = View.GONE
          }

     }

}