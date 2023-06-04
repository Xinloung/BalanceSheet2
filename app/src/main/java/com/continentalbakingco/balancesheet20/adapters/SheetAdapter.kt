package com.continentalbakingco.balancesheet20.adapters

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.continentalbakingco.balancesheet20.R
import com.continentalbakingco.balancesheet20.model.BalanceSheet
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SheetAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private  var balanceSheet : List<BalanceSheet> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BalanceSheetViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.balance_sheet_item_list,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ctx = holder.itemView.context
        when(holder){
            is BalanceSheetViewHolder ->{
                holder.bind(balanceSheet[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return balanceSheet.size
    }
    fun submitList(list : List<BalanceSheet>){
        balanceSheet = list
        notifyDataSetChanged()
    }

    class BalanceSheetViewHolder constructor(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val tvBal : TextView = itemView.findViewById(R.id.tv_bal_number)
        private val flList : FrameLayout = itemView.findViewById(R.id.fl_list)
        private val ivSheetList : ImageView = itemView.findViewById(R.id.iv_sheet_list)
        private val tvDate : TextView = itemView.findViewById(R.id.tv_date)
        private val tvNet : TextView = itemView.findViewById(R.id.tv_net)
        private val decimalFormat : DecimalFormat = DecimalFormat("0.00")


        fun bind(balanceSheet: BalanceSheet) {
            decimalFormat.groupingSize = 3
            decimalFormat.isGroupingUsed = true
            tvBal.text = balanceSheet.BalanceSheetNumber
            tvDate.text = "Date: ${formatDate(balanceSheet.Sheet_Date)}"
            tvNet.text = "Net Amount: $ ${decimalFormat.format(balanceSheet.NetOverageShortage)}"


                when {
                    balanceSheet.NetOverageShortage < 0 -> {
                        ivSheetList.setImageResource(R.drawable.ic_baseline_error_outline_red_24)
                    }
                    balanceSheet.NetOverageShortage > 0 -> {
                        ivSheetList.setImageResource(R.drawable.ic_baseline_error_outline_yellow_24)
                    }
                    else -> {
                        ivSheetList.setImageResource(R.drawable.ic_baseline_check_circle_outline_green_24)
                    }
                }

        }
        private fun formatDate(dateString: String) : String{
            val unformattedDate : Date?
            val formattedDate : SimpleDateFormat = SimpleDateFormat(" MMM dd, yyyy", Locale.ENGLISH)
            val dateFormatter : SimpleDateFormat? = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            unformattedDate = dateFormatter?.parse(dateString)
            return formattedDate.format(unformattedDate)
        }
    }
}