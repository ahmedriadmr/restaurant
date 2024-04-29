package com.doubleclick.restaurant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.doubleclick.domain.model.table.Table
import com.doubleclick.rovleapp.databinding.TextSpinnerLayoutBinding


class SpinnerAdapterTable(
    private val tables: List<Table>
) : BaseAdapter() {


    override fun getCount(): Int {
        return tables.size
    }

    override fun getItem(i: Int): Any {
        return i;
    }

    override fun getItemId(i: Int): Long {
        return i.toLong();
    }

    override fun getView(i: Int, p1: View?, viewGroup: ViewGroup?): View {
        val rootView = TextSpinnerLayoutBinding.inflate(LayoutInflater.from(viewGroup!!.context))
        rootView.textSpinner.text = tables[i].name
        return rootView.root;
    }

}