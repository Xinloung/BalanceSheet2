package com.continentalbakingco.balancesheet20.view

import android.content.Intent
import android.os.Bundle
import com.continentalbakingco.balancesheet20.R
import com.continentalbakingco.balancesheet20.databinding.ActivityRegistrationBinding
import com.continentalbakingco.balancesheet20.viewmodel.RegistrationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RegistrationActivity : RegistrationViewModel() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = bind.root
        setContentView(view)
        setSupportActionBar(bind.tbRegister)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
 override fun onStart(){
     super.onStart()
    removeStatusBar()
     GlobalScope.launch(Dispatchers.IO){
         withContext(Dispatchers.Main)
         {
            routeInfo(bind.spRouteId)

             locationInit(bind.spLocationId, bind.spLocatorId)
         }
     }



    bind.tvLogin.setOnClickListener {
        val intent : Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
     bind.btnRegister.setOnClickListener {
        GlobalScope.launch(Dispatchers.IO) {
           withContext(Dispatchers.Main)
           {
               if(checkInput()){
                   createUser()
               }
           }
        }

     }
     bind.tvTermsAndConditions.setOnClickListener {
         mShowDialog(resources.getString(R.string.title_terms_and_conditions),resources.getString(R.string.terms_and_conditions),false )
     }

 }



}



