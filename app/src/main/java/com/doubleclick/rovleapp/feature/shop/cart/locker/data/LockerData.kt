package com.doubleclick.restaurant.feature.shop.cart.locker.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LockerData(
    val cod_estado: String,
    val cod_homepaq: String,
    val cod_localidad: String,
    val cod_postal: String,
    val cod_provincia: String,
    val coorXETRS89: String,
    val coorXWGS84: String,
    val coorYETRS89: String,
    val coorYWGS84: String,
    val created_at: String,
    val des_canal: String,
    val des_via: String,
    val det_canal: String,
    val direccion: String,
    val fec_alta: String,
    val fec_instalacion: String,
    val id: String,
    val id_fabricante: String,
    val ind_admision: String,
    val ind_horario: String,
    val latitudETRS89: String,
    val latitudWGS84: String,
    val longitudETRS89: String,
    val longitudWGS84: String,
    val modelo: String,
    val nom_fabricante: String,
    val num_modulos: String,
    val num_ubi__muy_pequeno: String,
    val num_ubi_egrande: String,
    val num_ubi_grande: String,
    val num_ubi_mediano: String,
    val num_ubi_pequeno: String,
    val numero: String,
    val tipo_homepaq: String,
    val updated_at: String,
    var isSelected: Boolean
) : Parcelable{

    companion object {
        val empty = LockerData(
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",false


            )
    }
}