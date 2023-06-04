package com.continentalbakingco.balancesheet20.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.continentalbakingco.balancesheet20.R
import java.util.zip.Inflater

class CountryAdapter(internal var ctx : Context, internal var flagImages : IntArray, internal var country : MutableList<String>):BaseAdapter() {
internal var inflater : LayoutInflater
init {
    inflater = LayoutInflater.from(ctx)
}
    override fun getCount(): Int {
        return flagImages.size
    }

    override fun getItem(position: Int): Any? {
        return  null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       val view = inflater.inflate(R.layout.spinner_country, null)
        val icon = view.findViewById<View>(R.id.iv_country) as ImageView?
        val text = view.findViewById<View>(R.id.tv_country) as TextView?
        icon!!.setImageResource(flagImages[position])
        text!!.text = country[position]
        return view
    }

}