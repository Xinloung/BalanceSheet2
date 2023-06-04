package com.continentalbakingco.balancesheet20.view

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.continentalbakingco.balancesheet20.R
import com.continentalbakingco.balancesheet20.databinding.ActivityDashboardBinding
import com.continentalbakingco.balancesheet20.viewmodel.DashboardViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class DashboardActivity : DashboardViewModel() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDashboardBinding.inflate(layoutInflater)
        val view  = binding.root
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        removeStatusBar()
        userId = intent.getIntExtra("Id",0)
        username = intent.getStringExtra("FirstName").toString()
        routeID = intent.getIntExtra("RouteId", 0)

            getRouteNumber(routeID)
            initAllData(userId)

        binding.dbAddBalancesheet.setOnClickListener {
                if(isLoggedIn(userId)) {
                    val intent: Intent = Intent(this@DashboardActivity, BalanceSheetActivity::class.java)
                    intent.putExtra("uid", userId)
                    intent.putExtra("FirstName", username )
                    intent.putExtra("RouteId", routeID)
                    intent.putExtra("RouteNumber", routeNumber)
                    intent.putExtra("RouteTypeId", routeTypeId)
                    startActivity(intent)
                }else{
                    mShowDialog(getString(R.string.title_login_required), getString(R.string.dialog_body_login_required), true)
                }
            }
        binding.ivRefresh.setOnClickListener {
                initAllData(userId)
        }

    }


}