package com.continentalbakingco.balancesheet20.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.continentalbakingco.balancesheet20.R
import com.continentalbakingco.balancesheet20.databinding.ActivityBalanceSheetBinding
import com.continentalbakingco.balancesheet20.model.JSONRoute
import com.continentalbakingco.balancesheet20.viewmodel.BalanceSheetViewModel
import java.lang.Exception

class BalanceSheetActivity : BalanceSheetViewModel() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBalanceSheetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.tbBalanceSheet)
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
    override fun onStart() {
        super.onStart()
        routeNumber =  intent.getStringExtra("RouteNumber")!!
        routeTypeId = intent.getIntExtra("RouteTypeId", 0)
        binding.user.text = intent.getStringExtra("FirstName")

         initSpinnerDefaultRoute(binding.spRouteId, routeNumber)
        initSpinnerDefaultRouteType(binding.spRouteType, routeTypeId)
        initAccountType(binding.spAccountType)

       binding.tvDate.setOnClickListener { view ->
             showDatePicker(view)
       }

        binding.btnCalculate.setOnClickListener {

            try {
                calculate()
                binding.btnVerify.isEnabled = true
                binding.btnVerify.setBackgroundColor(resources.getColor(R.color.national_yellow))
            }catch (e : Exception){

            }
        }
        binding.btnVerify.setOnClickListener {
            try {
                binding.btnCalculate.isEnabled =false
                binding.btnCalculate.setBackgroundColor(resources.getColor(R.color.material_on_primary_disabled))
                binding.btnSubmit.setBackgroundColor(resources.getColor(R.color.green))
            }catch (e : Exception){

            }
        }
        binding.btnSubmit.setOnClickListener {
            try {
                binding.btnVerify.isEnabled = false
                binding.btnCalculate.isEnabled = false
            }catch (e : Exception){

            }
        }
        addZero(binding.etRouteDiscount)
        addZero(binding.etTotalToAccountFor)
        addZero(binding.etAccumulatedCustomerBalance)
        addZero(binding.etNonARCreditSales)
        addZero(binding.etCustomerBalanceCollection)
        addZero(binding.etCustomerBalance)
        addZero(binding.etEasterAccumulatedCustomerBalance)
        addZero(binding.etEasterNonARCreditSales)
        addZero(binding.etEasterCustomerBalanceCollection)
        addZero(binding.etEasterCustomerBalance)
        addZero(binding.etSampleRecoveryAccumulated)
        addZero(binding.etSampleIssuedToday)
        addZero(binding.etTotalSampleRecovery)
        addZero(binding.etAccountReceivables)
        addZero(binding.etSales)
        addZero(binding.etReturns)
        addZero(binding.etBinTransferCreditNotes)
        addZero(binding.etReturnedCheques)
        addZero(binding.etPostDatedCheques)
        addZero(binding.etTaxExemption)
        addZero(binding.etRouteExpense)
        addZero(binding.etExcessCharges)
        addZero(binding.etTruckStock)
        addZero(binding.etMisc)
        addZero(binding.etTotalAccountedFor)
        addZero(binding.etNetOverageOrShortage)
    }

}