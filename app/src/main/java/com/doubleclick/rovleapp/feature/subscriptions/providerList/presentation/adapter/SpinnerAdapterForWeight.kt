package com.doubleclick.rovleapp.feature.subscriptions.providerList.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.feature.subscriptions.providerList.data.showPlan.Size

class SpinnerAdapterForWeight(
    var context: Context,
    private val weightSize: List<Size>
) : BaseAdapter() {

    override fun getCount(): Int {
        return weightSize.size
    }

    override fun getItem(i: Int): Any {
        return weightSize[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup?): View {
        var view = convertView
        val viewHolder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.text_spinner_layout, viewGroup, false)
            viewHolder = ViewHolder()
            viewHolder.weight = view.findViewById(R.id.text_spinner)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val currentWeight = weightSize[i]
        viewHolder.weight?.text = context.getString(R.string.weight_text, currentWeight.size.name, currentWeight.size.weight)
        return view!!
    }

    private class ViewHolder {
        var weight: TextView? = null
    }
}