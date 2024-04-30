package com.doubleclick.restaurant.adapter.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.doubleclick.restaurant.R


class SpinnerAdapter(
    var context: Context, private val addressModelList: List<String>, val color: Int, val gravity: Int
) : BaseAdapter() {


    override fun getCount(): Int {
        return addressModelList.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val rootView: View
        val holder: ViewHolder

        if (convertView == null) {
            rootView = LayoutInflater.from(context).inflate(R.layout.text_spinner_layout, viewGroup, false)
            holder = ViewHolder(rootView.findViewById(R.id.text_spinner))
            rootView.tag = holder
        } else {
            rootView = convertView
            holder = rootView.tag as ViewHolder
        }

        holder.text.setBackgroundColor(ContextCompat.getColor(context, color))
        holder.text.text = addressModelList[i]
        holder.text.gravity = gravity

        return rootView
    }

    private class ViewHolder(val text: TextView)
}