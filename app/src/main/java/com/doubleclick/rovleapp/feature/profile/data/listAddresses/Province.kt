package com.doubleclick.rovleapp.feature.profile.data.listAddresses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Province(
    val country_id: Int,
    val created_at: String,
    val id: Int,
    val name: String,
    val provinceCode:String,
    val updated_at: String
) : Parcelable{
    companion object {
        val empty = Province(-1,"",-1,"","",""
        )
    }
}