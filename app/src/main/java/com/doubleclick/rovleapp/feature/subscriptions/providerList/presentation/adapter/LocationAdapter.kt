package com.doubleclick.restaurant.feature.subscriptions.providerList.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.feature.profile.data.listAddresses.AddressesData

class LocationAdapter (
    var context: Context,
    private var addresses: List<AddressesData>
) : BaseAdapter() {


    override fun getCount(): Int {
        return addresses.size
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
            view = LayoutInflater.from(context).inflate(R.layout.layout_location, viewGroup, false)
            viewHolder = ViewHolder()
            viewHolder.name = view.findViewById(R.id.location_address)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.name?.text = addresses[i].address
        return view!!
    }

    private class ViewHolder {
        var name: TextView? = null
    }
}