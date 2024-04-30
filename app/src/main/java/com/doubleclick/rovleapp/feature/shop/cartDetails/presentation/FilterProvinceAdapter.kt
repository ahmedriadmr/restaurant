package com.doubleclick.restaurant.feature.shop.cartDetails.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.feature.shop.cartDetails.data.filterCities.Province

class FilterProvinceAdapter (
    var context: Context,
    private val provinces: List<Province>
) : BaseAdapter() {


    override fun getCount(): Int {
        return provinces.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup?): View {
        var view = convertView
        val viewHolder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_item_country_province_city, viewGroup, false)
            viewHolder = ViewHolder()
            viewHolder.name = view.findViewById(R.id.name)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.name?.text = provinces[i].name
        return view!!
    }

    private class ViewHolder {
        var name: TextView? = null
    }
}