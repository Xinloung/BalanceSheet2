package com.continentalbakingco.balancesheet20.viewmodel

import android.app.DatePickerDialog
import android.content.res.AssetManager
import android.graphics.Typeface
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.continentalbakingco.balancesheet20.R
import com.continentalbakingco.balancesheet20.databinding.ActivityBalanceSheetBinding
import com.continentalbakingco.balancesheet20.model.JSONAccountType
import com.continentalbakingco.balancesheet20.model.JSONRoute
import com.continentalbakingco.balancesheet20.model.JSONRouteType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.lang.reflect.Type
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

open class BalanceSheetViewModel : BaseActivity() {
        lateinit var binding: ActivityBalanceSheetBinding
        private var statementOpeningBalance: Double? = 0.0
        private var pickupLoadValue: Double? = 0.0
        private var routeDiscount: Double? = 0.0
        private var totalToAccountedFor: Double? = 0.0
        private var accountsReceivables: Double? = 0.0
        private var cashChequeSales: Double? = 0.0
        private var accumulatedCustomerBalance: Double? = 0.0
        private var customerBalanceCollector: Double? = 0.0
        private var nonARCreditSales: Double? = 0.0
        private var totalCustomerBalance: Double? = 0.0
        private var returnDamage: Double? = 0.0
        private var creditNotesBinTransfers: Double? = 0.0
        private var returnedCheques: Double? = 0.0
        private var postDatedCheques: Double? = 0.0
        private var taxExemption: Double? = 0.0
        private var routeExpense: Double? = 0.0
        private var excessCharges: Double? = 0.0
        private var truckStock: Double? = 0.0
        private var otherMisc: Double? = 0.0
        private var totalAccountedFor: Double? = 0.0
        private var easterAccumulatedCustomerBalance: Double? = 0.0
        private var easterNonARCreditSales: Double? = 0.0
        private var easterCustomerBalanceCollection: Double? = 0.0
        private var easterRemainingCustomerBalance: Double? = 0.0
        private var sampleRecoveryAccumulated: Double? = 0.0
        private var samplesIssuedToday: Double? = 0.0
        private var totalSamples: Double? = 0.0
        private var comments: String? = ""
        private var netOverageShortage: Double? = 0.0
        private var myDay: Int = 0
        private var myMonth: Int = 0
        private var myYear: Int = 0
        private lateinit var dbDate: String
        private val decimalFormat: DecimalFormat = DecimalFormat("0.00")
        private val buffer: DoubleArray = DoubleArray(6)
        private var isBusy: Boolean = false



        //Initialize Route Spinner
        fun initSpinnerDefaultRoute(spinner: Spinner, default: String) {
                var list: MutableList<String> = mutableListOf()
                var call: Call<List<JSONRoute>>
                GlobalScope.launch(Dispatchers.IO) {
                        try{
                        call = utilMan.utilResponseRoute()
                        withContext(Dispatchers.Main) {
                                call.enqueue(object : Callback<List<JSONRoute>> {
                                        override fun onResponse(call: Call<List<JSONRoute>>, response: Response<List<JSONRoute>>) {
                                                response.body()!!.forEach { item ->
                                                        list.add(item.RouteNum)
                                                }
                                                val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.spinner_item, list)
                                                spinner.adapter = spinnerAdapter
                                                val pos: Int = spinnerAdapter.getPosition(default)
                                                spinner.setSelection(pos)
                                        }
                                        override fun onFailure(call: Call<List<JSONRoute>>, t: Throwable) {
                                                showSnackBar(getString(R.string.err_msg_check_internet), true)
                                        }
                                })
                        }
                }catch (e: Exception){
                                showSnackBar(getString(R.string.err_msg_check_internet), true)
                                mShowDialog("Error - ${e.cause}", e.message.toString(), false)
                        }
                }
        }

        //Initialize Route Type Spinner
        fun initSpinnerDefaultRouteType(spinner: Spinner, rTypeId: Int) {
                var list: MutableList<String> = mutableListOf()
                var call: Call<List<JSONRouteType>>
                GlobalScope.launch(Dispatchers.IO) {
                        try {
                                call = utilMan.utilResponseRouteType()
                                withContext(Dispatchers.Main) {
                                        currentRouteType(rTypeId)
                                        call.enqueue(object : Callback<List<JSONRouteType>> {
                                                override fun onResponse(call: Call<List<JSONRouteType>>, response: Response<List<JSONRouteType>>) {
                                                        if (response.isSuccessful) {
                                                                response.body()!!.forEach { it ->
                                                                        list.add(it.RouteTypeDescription)
                                                                }
                                                                var pos: Int = 0
                                                                val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.spinner_item, list)
                                                                spinner.adapter = spinnerAdapter
                                                                pos = spinnerAdapter.getPosition(routeNumber)
                                                                spinner.setSelection(pos)
                                                        }
                                                }
                                                override fun onFailure(call: Call<List<JSONRouteType>>, t: Throwable) {
                                                        mShowDialog(getString(R.string.err_msg_check_internet), t.toString(), false)
                                                }
                                        })
                                }
                        }catch (e : Exception){
                                showSnackBar(getString(R.string.err_msg_check_internet), true)
                                mShowDialog("Error - ${e.cause}", e.message.toString(), false)
                        }
                }
        }

        //Initialize Account type Spinner
        fun initAccountType(spinner: Spinner) {
                var list: MutableList<String> = mutableListOf()
                var response: Call<List<JSONAccountType>>
                GlobalScope.launch(Dispatchers.IO) {
                        try {
                                response = utilMan.utilResponseAccountType()
                                withContext(Dispatchers.Main) {
                                        response.enqueue(object : Callback<List<JSONAccountType>> {
                                                override fun onResponse(call: Call<List<JSONAccountType>>, response: Response<List<JSONAccountType>>) {
                                                        response.body()!!.forEach { it ->
                                                                list.add(it.Description)
                                                        }
                                                        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.spinner_item, list)
                                                        spinner.adapter = spinnerAdapter
                                                }
                                                override fun onFailure(call: Call<List<JSONAccountType>>, t: Throwable) {
                                                                mShowDialog("Connection Error!", getString(R.string.err_msg_check_internet), false)
                                                }
                                        })
                                }

                        }catch (e : Exception){
                                showSnackBar(getString(R.string.err_msg_check_internet), true)
                                mShowDialog("Error - ${e.cause}", e.message.toString(), false)
                        }
                }
        }

        //Creates a Calendar to store dates
        fun showDatePicker(view: View){
                val myCalendar = Calendar.getInstance()
                val year = myCalendar.get(Calendar.YEAR)
                val month = myCalendar.get(Calendar.MONTH)
                val day = myCalendar.get(Calendar.DAY_OF_MONTH)
                val assetManager : AssetManager = this.assets
                val formattedDate : SimpleDateFormat = SimpleDateFormat("yyyy-MMM-dd", Locale.ENGLISH)
                val unformattedDate : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val dpDialog : DatePickerDialog = DatePickerDialog(this, { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                        val selectedDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                LocalDate.of(selectedYear, selectedMonth + 1, selectedDayOfMonth)
                        } else {
                                "$selectedYear-${selectedMonth + 1}-$selectedDayOfMonth"
                        }
                        dbDate = selectedDate.toString()
                        myDay =   selectedDayOfMonth
                        myMonth = (selectedMonth + 1)
                        myYear = selectedYear
                        val tvDate = findViewById<TextView>(R.id.tvDate)
                        tvDate.typeface = Typeface.createFromAsset(assetManager, "Montserrat-Bold.ttf")
                        tvDate.text = formattedDate.format(unformattedDate.parse(dbDate))
                }, year, month, day)
                dpDialog.datePicker.maxDate = myCalendar.timeInMillis
                dpDialog.show()
        }

        //Get Route Number
        private suspend fun currentRouteType(rTypeId: Int) {
                var response: Call<JSONRouteType>
                try {
                        response = utilMan.utilResponseRouteType(rTypeId)
                        response.enqueue(object : Callback<JSONRouteType> {
                                override fun onResponse(call: Call<JSONRouteType>, response: Response<JSONRouteType>) {
                                        if (response.isSuccessful) {
                                                routeNumber = response.body()!!.RouteTypeDescription
                                        }
                                }
                                override fun onFailure(call: Call<JSONRouteType>, t: Throwable) {
                                        println(t)
                                }
                        })

                } catch (e: Exception) {

                }

        }

        fun addZero(et: EditText) {
                if(TextUtils.isEmpty(et.text.toString())){
                        et.setText("0.00")
                }
                et.setOnFocusChangeListener { v, hasFocus ->
                        when{
                                !hasFocus && TextUtils.isEmpty(et.text.toString())->{et.setText("0.00") }
                                hasFocus && et.text.toString() == "0.00" -> {et.setText("")}
                                TextUtils.isEmpty(et.text.toString()) ->{et.setText("")}
                        }
                }

        }

        fun calculate(){

                val calValueStat : Double = if(TextUtils.isEmpty(binding.etStatementOpening?.text.toString())){ 0.00 }else{ binding.etStatementOpening?.text.toString().toDouble()}
                val calValueLoad : Double = if(TextUtils.isEmpty(binding.etLoadValue?.text.toString())){ 0.00 }else{ binding.etLoadValue?.text.toString().toDouble()}
                val calValueDiscount : Double = if(TextUtils.isEmpty(binding.etRouteDiscount?.text.toString())){ 0.00 }else{ binding.etRouteDiscount?.text.toString().toDouble()}
                val calValueAccFor: Double
                val calValueRec : Double = if(TextUtils.isEmpty(binding.etAccountReceivables?.text.toString())){ 0.00 }else{ binding.etAccountReceivables?.text.toString().toDouble()}
                val calValueSal : Double = if(TextUtils.isEmpty(binding.etSales?.text.toString())){ 0.00 }else{ binding.etSales?.text.toString().toDouble()}
                val calValueAccCus : Double = if(TextUtils.isEmpty(binding.etAccumulatedCustomerBalance?.text.toString())){ 0.00 }else{ binding.etAccumulatedCustomerBalance?.text.toString().toDouble()}
                val calValueBalCol: Double =  if(TextUtils.isEmpty(binding.etCustomerBalanceCollection?.text.toString())){ 0.00 }else{binding.etCustomerBalanceCollection?.text.toString().toDouble()}
                val calValueARCrdSal : Double =  if(TextUtils.isEmpty(binding.etNonARCreditSales?.text.toString())){ 0.00 }else{ binding.etNonARCreditSales?.text.toString().toDouble()}
                val calValueRet : Double = if(TextUtils.isEmpty(binding.etReturns?.text.toString())){ 0.00 }else{ binding.etReturns?.text.toString().toDouble()}
                val calValueBinTransCrdNotes : Double = if(TextUtils.isEmpty(binding.etBinTransferCreditNotes?.text.toString())){ 0.00 }else{ binding.etBinTransferCreditNotes?.text.toString().toDouble()}
                val calValueRetCheques : Double = if(TextUtils.isEmpty(binding.etReturnedCheques?.text.toString())){ 0.00 }else{ binding.etReturnedCheques?.text.toString().toDouble()}
                val calValuePostCheqs : Double = if(TextUtils.isEmpty(binding.etPostDatedCheques?.text.toString())){ 0.00 }else{ binding.etPostDatedCheques?.text.toString().toDouble()}
                val calValueRouteExpense : Double = if(TextUtils.isEmpty(binding.etRouteExpense?.text.toString())){ 0.00 }else{ binding.etRouteExpense?.text.toString().toDouble()}
                val calValueExcess : Double = if(TextUtils.isEmpty(binding.etExcessCharges?.text.toString())){ 0.00 }else{ binding.etExcessCharges?.text.toString().toDouble()}
                val calValueTruckStock : Double = if(TextUtils.isEmpty(binding.etTruckStock?.text.toString())){ 0.00 }else{ binding.etTruckStock?.text.toString().toDouble()}
                val calValueMisc : Double = if(TextUtils.isEmpty(binding.etMisc?.text.toString())){ 0.00 }else{ binding.etMisc?.text.toString().toDouble()}
                val calValueTaxExemp : Double= if(TextUtils.isEmpty(binding.etTaxExemption?.text.toString())){ 0.00 }else{ binding.etTaxExemption?.text.toString().toDouble()}
                val calValueTotAccFor: Double
                val calValueNetOverShort: Double
                val calCustomerBalance : Double
                val calEasterAccumulatedCustomerBalance : Double = if(TextUtils.isEmpty(binding.etEasterAccumulatedCustomerBalance?.text.toString())){ 0.00 }else{ binding.etEasterAccumulatedCustomerBalance?.text.toString().toDouble()}
                val calEasterNonARCreditSales : Double =if(TextUtils.isEmpty(binding.etEasterNonARCreditSales?.text.toString())){ 0.00 }else{ binding.etEasterNonARCreditSales?.text.toString().toDouble()}
                val calEasterCustomerBalanceCollection : Double =if(TextUtils.isEmpty(binding.etEasterCustomerBalanceCollection?.text.toString())){ 0.00 }else{ binding.etEasterCustomerBalanceCollection?.text.toString().toDouble()}
                val calEasterCustomerBalance : Double
                val calSampleRecoveryAccumulated : Double =if(TextUtils.isEmpty(binding.etSampleRecoveryAccumulated?.text.toString())){ 0.00 }else{ binding.etSampleRecoveryAccumulated?.text.toString().toDouble()}
                val calSampleIssuedToday : Double =if(TextUtils.isEmpty(binding.etSampleIssuedToday?.text.toString())){ 0.00 }else{ binding.etSampleIssuedToday?.text.toString().toDouble()}
                val calTotalSampleRecovery : Double
                val etUserComments : String = binding.etComments?.text.toString()

                val ttAccountFor : Double = calValueStat.plus(calValueLoad).minus(calValueDiscount)
                calCustomerBalance = calValueAccCus.plus(calValueARCrdSal).minus(calValueBalCol)
                calValueAccFor = ttAccountFor
                calEasterCustomerBalance = calEasterAccumulatedCustomerBalance.plus(calEasterNonARCreditSales).minus(calEasterCustomerBalanceCollection)
                calTotalSampleRecovery = calSampleRecoveryAccumulated.plus(calSampleIssuedToday)
                val ttAccountedFor : Double = calValueRec.plus(calValueSal).plus(calValueRet).plus(calValueBinTransCrdNotes).plus(calValueRetCheques)
                        .plus(calValuePostCheqs).plus(calValueRouteExpense).plus(calValueExcess).plus(calValueTruckStock)
                        .plus(calValueMisc).plus(calValueTaxExemp).plus(calCustomerBalance).plus(calEasterCustomerBalance).plus(calTotalSampleRecovery)
                calValueTotAccFor = ttAccountedFor

                val nOverageOrShortage = ttAccountedFor.minus(ttAccountFor)
                calValueNetOverShort = nOverageOrShortage

                decimalFormat.groupingSize = 3
                decimalFormat.isGroupingUsed = true
                decimalFormat.isDecimalSeparatorAlwaysShown = true

                buffer[0] = calValueAccFor
                buffer[1] = calValueTotAccFor
                buffer[2] = calValueNetOverShort
                buffer[3] = calCustomerBalance
                buffer[4] = calEasterCustomerBalance
                buffer[5] = calTotalSampleRecovery

                binding.etTotalToAccountFor?.setText(decimalFormat.format(buffer[0]).toString())
                binding.etTotalAccountedFor?.setText(decimalFormat.format(buffer[1]).toString())
                binding.etNetOverageOrShortage?.setText(decimalFormat.format(buffer[2]).toString())
                binding.etCustomerBalance?.setText(decimalFormat.format(buffer[3]).toString())
                binding.etEasterCustomerBalance?.setText(decimalFormat.format(buffer[4]).toString())
                binding.etTotalSampleRecovery?.setText(decimalFormat.format(buffer[5]).toString())

                statementOpeningBalance =  calValueStat
                pickupLoadValue = calValueLoad
                routeDiscount = calValueDiscount
                totalToAccountedFor = calValueAccFor
                accountsReceivables = calValueRec
                cashChequeSales = calValueSal
                accumulatedCustomerBalance = calValueAccCus
                customerBalanceCollector = calValueBalCol
                nonARCreditSales = calValueARCrdSal
                returnDamage = calValueRet
                creditNotesBinTransfers = calValueBinTransCrdNotes
                returnedCheques = calValueRetCheques
                postDatedCheques = calValuePostCheqs
                routeExpense = calValueRouteExpense
                excessCharges = calValueExcess
                truckStock = calValueTruckStock
                otherMisc =calValueMisc
                taxExemption = calValueTaxExemp
                totalAccountedFor = calValueTotAccFor
                netOverageShortage = calValueNetOverShort
                totalCustomerBalance = calCustomerBalance
                easterAccumulatedCustomerBalance = calEasterAccumulatedCustomerBalance
                easterNonARCreditSales = calEasterNonARCreditSales
                easterCustomerBalanceCollection  = calEasterCustomerBalanceCollection
                easterRemainingCustomerBalance  = calEasterCustomerBalance
                sampleRecoveryAccumulated = calSampleRecoveryAccumulated
                samplesIssuedToday  = calSampleIssuedToday
                totalSamples = calTotalSampleRecovery
                comments = etUserComments

                if (nOverageOrShortage < 0) {
                       binding.etNetOverageOrShortage?.setTextColor(ContextCompat.getColor(this, R.color.red_900))
                } else if(nOverageOrShortage > 0)  {
                      binding.etNetOverageOrShortage?.setTextColor(ContextCompat.getColor(this, R.color.national_yellow))
                }else if (nOverageOrShortage == 0.00){
                       binding.etNetOverageOrShortage?.setTextColor(ContextCompat.getColor(this, R.color.green))
                }
        }
}
