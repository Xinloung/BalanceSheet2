package com.continentalbakingco.balancesheet20.viewmodel

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.continentalbakingco.balancesheet20.R
import com.continentalbakingco.balancesheet20.adapters.SheetAdapter
import com.continentalbakingco.balancesheet20.databinding.ActivityDashboardBinding
import com.continentalbakingco.balancesheet20.model.BalanceSheet
import com.continentalbakingco.balancesheet20.repository.UtilManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

open class DashboardViewModel : BaseActivity(){

private lateinit var balanceSheetAdapter : SheetAdapter
lateinit var  binding: ActivityDashboardBinding

    fun initAllData(id : Int){
        addDataSet(id)
        initRecyclerView()
    }

     private fun addDataSet(id : Int){
        binding.pbDashboard.visibility = View.VISIBLE
        binding.flDashboard.isEnabled = false
        var call : Call<List<BalanceSheet>>
        val util : UtilManager = UtilManager()

            GlobalScope.launch(Dispatchers.IO) {
               try {
                withContext(Dispatchers.Main) {
                    call = util.utilResponseGetMySheets(id)

                    call.enqueue(object : Callback<List<BalanceSheet>> {
                        override fun onResponse(
                            call: Call<List<BalanceSheet>>,
                            response: Response<List<BalanceSheet>>
                        ) {
                            if (response.isSuccessful) {
                                balanceSheetAdapter.submitList(response.body()!!)
                                binding.tvTotalRecords.text = balanceSheetAdapter.itemCount.toString()
                            }
                        }
                        override fun onFailure(call: Call<List<BalanceSheet>>, t: Throwable) {
                            showSnackBar(getString(R.string.err_msg_check_internet), true)
                            println(t)
                        }

                    })
                }
            }catch (e : Exception){
                   mShowDialog("Error", e.localizedMessage, false)
               }finally {
                   withContext(Dispatchers.Main){
                       binding.pbDashboard.visibility = View.GONE
                       binding.flDashboard.isEnabled = true
                   }
               }

        }



    }

    private fun  initRecyclerView(){
       binding.rvBalanceSheets.apply{
            layoutManager = LinearLayoutManager(this@DashboardViewModel)
            balanceSheetAdapter = SheetAdapter()
            adapter = balanceSheetAdapter
        }
    }

}