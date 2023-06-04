package com.continentalbakingco.balancesheet20.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.continentalbakingco.balancesheet20.BuildConfig
import com.continentalbakingco.balancesheet20.R
import com.continentalbakingco.balancesheet20.databinding.ActivityLoginBinding
import com.continentalbakingco.balancesheet20.model.JsonUser
import com.continentalbakingco.balancesheet20.viewmodel.Authenticator
import com.continentalbakingco.balancesheet20.viewmodel.BaseActivity
import com.continentalbakingco.balancesheet20.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import retrofit2.Response


class LoginActivity : LoginViewModel() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        setSupportActionBar(binding.flHeaderImage).apply {
            title = ""
        }

    }
    override fun onStart() {
        super.onStart()
        removeStatusBar()
        binding.tvCopyright.text = getString(R.string.copyright, year)
        binding.tvVersion.text = getString(R.string.build_version, BuildConfig.VERSION_NAME)
        binding.tvRegister.setOnClickListener {
            val intent : Intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            if(checkInput(binding.etEmail.text.toString(), binding.etPassword.text.toString())) {
                getUserData()
            }
        }
    }


}