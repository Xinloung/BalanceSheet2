package com.continentalbakingco.balancesheet20.viewmodel

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.continentalbakingco.balancesheet20.R
import com.continentalbakingco.balancesheet20.databinding.ActivityLoginBinding
import com.continentalbakingco.balancesheet20.model.JsonUser
import com.continentalbakingco.balancesheet20.view.DashboardActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import retrofit2.Response

open class LoginViewModel : BaseActivity() {
    lateinit var binding: ActivityLoginBinding
    val year : String = DateTime().year.toString()

    //Validate user Input
    fun checkInput(email : String, password : String) : Boolean{
        return when {
            TextUtils.isEmpty(email.trim{it <= ' '}) ->{
                showSnackBar(getString(R.string.err_msg_enter_email), true)
                false
            }
            !isValidEmail(binding.etEmail.text.toString().trim{it <= ' '}) ->{
                showSnackBar(getString(R.string.err_msg_invalid_email_address), true)
                false
            }
            TextUtils.isEmpty(password.trim{it <= ' '}) ->{
                showSnackBar(getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> true
        }
    }

    //Fetches User Data
    fun getUserData(){
        val auth : Authenticator = Authenticator()
        var user : JsonUser
        var response : Response<JsonUser>?
        val intent : Intent = Intent(this@LoginViewModel,DashboardActivity::class.java)

        binding.pbLogin.visibility = View.VISIBLE
        binding.btnLogin.isEnabled = false

        GlobalScope.launch(Dispatchers.IO) {
            try{
                response = auth.CheckEmail(binding.etEmail.text.toString().trim{it <= ' '})

                if (response!!.isSuccessful) {
                    user = response!!.body()!!
                     println("Response Code:${response!!.code()}")
                    if(auth.decrypt(user.Password.toByteArray(),binding.etPassword.text.toString().trim{it <= ' '})){
                        withContext(Dispatchers.Main) {
                            intent.putExtra("Id",user.Id)
                            intent.putExtra("Trn",user.TRN)
                            intent.putExtra("FirstName",user.FirstName)
                            intent.putExtra("LastName",user.LastName)
                            intent.putExtra("Email",user.Email)
                            intent.putExtra("RouteId",user.RouteId)
                            userId = user.Id
                            startActivity(intent)
                            finish()
                        }
                    }else{
                        withContext(Dispatchers.Main) {
                            showSnackBar(getString(R.string.err_msg_incorrect_email_and_password_combination), true)
                        }
                    }
                }else{
                    withContext(Dispatchers.Main) {
                        showSnackBar(getString(R.string.err_msg_incorrect_email_and_password_combination), true)
                        println("Response Code:${response!!.code()}")
                    }
                }
            }catch (e : Exception){
                withContext(Dispatchers.Main){
                    showSnackBar(getString(R.string.err_msg_check_internet), true)
                    Log.e("ERROR", e.toString())
                }
            }finally {
                withContext(Dispatchers.Main){
                    binding.btnLogin.isEnabled = true
                    binding.pbLogin.visibility = View.GONE
                }
            }
        }
    }
}