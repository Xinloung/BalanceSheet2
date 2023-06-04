package com.continentalbakingco.balancesheet20.viewmodel

import android.accounts.Account
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.DialogCompat
import androidx.core.content.ContextCompat
import androidx.core.view.size
import com.continentalbakingco.balancesheet20.R
import com.continentalbakingco.balancesheet20.`interface`.ApiRequest
import com.continentalbakingco.balancesheet20.adapters.CountryAdapter
import com.continentalbakingco.balancesheet20.constants.Constants
import com.continentalbakingco.balancesheet20.constants.Constants.BASE_URL
import com.continentalbakingco.balancesheet20.model.*
import com.continentalbakingco.balancesheet20.repository.UtilManager
import com.continentalbakingco.balancesheet20.view.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import okhttp3.internal.lockAndWaitNanos
import okhttp3.internal.wait
import okhttp3.internal.waitMillis
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import kotlin.reflect.typeOf

open class BaseActivity : AppCompatActivity() {
    //Global variables
    //Late initializations
    lateinit var currentDateTime: String
    private lateinit var showDialogBuilder: MaterialAlertDialogBuilder
    lateinit var jsonRouteList: List<JSONRoute>
    lateinit var jsonLocation: List<JsonLocation>
    lateinit var jsonLocator: List<JsonLocator>
    lateinit var jsonCountry: List<JsonCountry>

    //
    var routeID : Int = 0
    val utilMan : UtilManager = UtilManager()
    var userId : Int = 0
    var username : String? = ""
    var accountId : Int = 0
    var routeTypeId : Int = 0
    var routeType : String = ""
    var routeNumber = ""
    var countryCode : String = ""
    var locatorCode  : String =""
    var locationId : Int = 0
    var countryId : Int = 0

    //Creates theSnackBar Definition
    fun showSnackBar(message: String, hasError: Boolean) {

        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view

        if (hasError) {
            snackbarView.setBackgroundColor(
                    ContextCompat.getColor(this@BaseActivity, R.color.colorSnackBarError)
            )
        } else {
            snackbarView.setBackgroundColor(
                    ContextCompat.getColor(this@BaseActivity, R.color.colorSnackBarSuccess)
            )
        }
        snackbar.show()
    }

    //Check if email address is Valid
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //Initializes spinner
    fun routeInfo(spinner: Spinner){
        val list : MutableList<String> = mutableListOf()

            GlobalScope.launch(Dispatchers.IO) {
                val call: Call<List<JSONRoute>> = utilMan.utilResponseRoute()
                try {
                call.enqueue(object : Callback<List<JSONRoute>> {
                    override fun onResponse(call: Call<List<JSONRoute>>, response: Response<List<JSONRoute>>) {
                        if (response.isSuccessful) {
                            if (response.code() == 200) {
                                jsonRouteList = response.body()!!
                                jsonRouteList.forEach { it ->
                                    list.add(it.RouteNum)
                                    initSpinner(spinner, list)
                                }
                            }

                        }
                    }
                    override fun onFailure(call: Call<List<JSONRoute>>, t: Throwable) {
                       showSnackBar(resources.getString(R.string.err_msg_check_internet), true)
                    }
                })
            }catch (e : Exception){
                    if(e == IOException())
                    {
                        showSnackBar(getString(R.string.err_msg_unable_to_contact_server), true)
                    }
                }
        }
    }
    //Initializes spinner
    fun locationInit(spinner: Spinner, locationSpinner: Spinner){
        val list : ArrayList<JsonCountry> = ArrayList()
        list.clear()
        GlobalScope.launch(Dispatchers.IO) {
            val call: Call<List<JsonCountry>> = utilMan.utilResponseCountry()
            try {
                call.enqueue(object : Callback<List<JsonCountry>> {
                    override fun onResponse(call: Call<List<JsonCountry>>, response: Response<List<JsonCountry>>) {
                        if (response.isSuccessful) {
                            if (response.code() == 200) {
                                response.body()!!.forEach {
                                    list.add(it)
                                }
                                initCountrySpinner(spinner, list, locationSpinner)
                            }

                        }
                    }
                    override fun onFailure(call: Call<List<JsonCountry>>, t: Throwable) {
                        showSnackBar(resources.getString(R.string.err_msg_check_internet), true)
                    }
                })
            }catch (e : Exception){
                if(e == IOException())
                {
                    showSnackBar(getString(R.string.err_msg_unable_to_contact_server), true)
                }
            }
        }
    }

    //Creates spinner definition
    private fun initSpinner(spinner: Spinner, list : List<String>) {

            val spinnerAdapter: ArrayAdapter<*> = ArrayAdapter(this, R.layout.spinner_item,list )
            spinner.adapter = spinnerAdapter
            spinner.textAlignment = View.TEXT_ALIGNMENT_CENTER
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    getRouteID(spinner.selectedItem.toString())
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    println(spinner.selectedItemId.toString())
                }

            }
    }
    //Creates spinner definition
    private fun initCountrySpinner(spinner: Spinner, list : List<JsonCountry>, locationSpinner : Spinner) {
        var country : MutableList<String> = mutableListOf()
        list.forEach { it -> country.add( it.Country) }

        var spPosition = intArrayOf(R.drawable.jm, R.drawable.us, R.drawable.ca)

        val spinnerAdapter: ArrayAdapter<*> = ArrayAdapter(this, R.layout.spinner_item, country)

        val countrySpinnerAdapter  = CountryAdapter(applicationContext, spPosition, country )

        spinner.adapter = countrySpinnerAdapter

        spinner.textAlignment = View.TEXT_ALIGNMENT_CENTER

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var item =  list.findLast { it.Id == (position + 1) }

                println("parent: ${parent} + ${view}")

                if (item != null) {
                    countryCode = item.CountryCode
                    getLocatorCode(item.Id,locationSpinner)
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println(spinner.selectedItemId.toString())
            }

        }
    }
    private fun initLocatorSpinner(spinner: Spinner, list : List<JsonLocator>) {
        var locator : MutableList<String> = mutableListOf()
        list.forEach { it ->
            locator.add(it.LocationDescription)
        }
        val spinnerAdapter: ArrayAdapter<*> = ArrayAdapter(this, R.layout.spinner_item,locator )
        spinner.adapter = spinnerAdapter
        spinner.textAlignment = View.TEXT_ALIGNMENT_CENTER
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var item = list.find{it.LocationDescription == spinner.selectedItem}
               locatorCode = item!!.LocationCode
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println(spinner.selectedItemId.toString())
            }

        }
    }

    //removes the Status bar
    fun removeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    //creates Dialog window
    fun mShowDialog(title : String,message: String, hasError: Boolean){
        showDialogBuilder = MaterialAlertDialogBuilder(this)
       when{
                hasError-> {
                    showDialogBuilder.setTitle(title)
                            .setMessage(message)
                            .setNeutralButton("OK") { dialog, id ->
                                val intent : Intent = Intent(this@BaseActivity, LoginActivity::class.java)
                                startActivity(intent)
                            }
                    showDialogBuilder.setCancelable(false)
                    showDialogBuilder.show()
                }else ->{
                       showDialogBuilder.setTitle(title)
                               .setMessage(message)
                               .setNeutralButton("OK") { dialog, id ->
                               }
                       showDialogBuilder.setCancelable(false)
                       showDialogBuilder.show()
                }
       }
    }

    //Gets ID from spinner's selected value
    fun getRouteID(route : String){
        var call : Call<JSONRoute>
        GlobalScope.launch(Dispatchers.IO) {
            try {
                call = utilMan.utilResponseRoute(route)
                call.enqueue(object : Callback<JSONRoute> {
                    override fun onResponse(call: Call<JSONRoute>, response: Response<JSONRoute>) {
                        if (response.isSuccessful) {
                            routeID = response.body()!!.RouteID
                        }
                    }
                    override fun onFailure(call: Call<JSONRoute>, t: Throwable) {
                        println(t)
                    }

                })
            }catch (e : Exception)
            {
                showSnackBar(getString(R.string.err_msg_check_internet), true)
                mShowDialog("Error!", e.localizedMessage, false)
            }
        }
    }
    //Gets ID from spinner's selected value
    fun getLocatorCode(_countryId : Int, spinner: Spinner){
        println("Country ID: $_countryId")

        var callCountry : Call<JsonCountry>
        var callLocator : Call<List<JsonLocator>>
        GlobalScope.launch(Dispatchers.IO) {
            try {
                callCountry = utilMan.utilResponseCountry(_countryId)
                callCountry.enqueue(object : Callback<JsonCountry>{
                    override fun onResponse(call: Call<JsonCountry>, response: Response<JsonCountry>) {
                        if(response.code()==200){
                            countryId = response.body()!!.Id
                           countryCode = response.body()!!.CountryCode
                        }
                        println("${response.code()}")
                    }
                    override fun onFailure(call: Call<JsonCountry>, t: Throwable) {
                       showSnackBar(resources.getString(R.string.err_msg_check_internet), true)
                    }

                })
                callLocator = utilMan.utilResponseCountryList(_countryId)
                callLocator.enqueue(object : Callback<List<JsonLocator>> {
                    override fun onResponse(call: Call<List<JsonLocator>>, response: Response<List<JsonLocator>>) {
                       if(response.isSuccessful){
                         initLocatorSpinner(spinner = spinner,  response.body()!!)
                       }
                    }
                    override fun onFailure(call: Call<List<JsonLocator>>, t: Throwable) {
                        println(t)
                    }

                })
            }catch (e : Exception)
            {
                showSnackBar(getString(R.string.err_msg_check_internet), true)
                mShowDialog("Error!", e.localizedMessage, false)
            }
        }
    }

    fun getCountryLocation(id : Int){
        var call : Call<List<JsonLocator>>
        GlobalScope.launch(Dispatchers.IO) {
            try {
                call = utilMan.utilResponseCountryList(id)
                call.enqueue(object : Callback<List<JsonLocator>> {
                    override fun onResponse(call: Call<List<JsonLocator>>, response: Response<List<JsonLocator>>) {
                        if(response.isSuccessful){
                            jsonLocator = response.body()!!
                        }
                    }
                    override fun onFailure(call: Call<List<JsonLocator>>, t: Throwable) {
                        println(t)
                    }

                })
            }catch (e: Exception){

            }
        }
    }
    //Gets the Route Number
    fun getRouteNumber(id : Int){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    routeNumber = utilMan.utilResponseGetMyRouteId(id).body()!!.RouteNum
                    routeTypeId = utilMan.utilResponseGetMyRouteId(id).body()!!.RouteTypeID
                }
            }catch (e : Exception){
                withContext(Dispatchers.Main) {
                    showSnackBar(getString(R.string.err_msg_check_internet), true)
                    mShowDialog("Error - ${e.cause} ", e.localizedMessage, false)
                }
            }
        }
    }

    //Checks if the user is logged in
    fun isLoggedIn(id : Int) : Boolean{
        return id != 0
    }

}