package com.doubleclick.domain.repo

import com.doubleclick.domain.model.Message
import com.doubleclick.domain.model.auth.*
import com.doubleclick.domain.model.carts.add.Cart
import com.doubleclick.domain.model.carts.get.Carts
import com.doubleclick.domain.model.category.add.Category
import com.doubleclick.domain.model.category.get.Categories
import com.doubleclick.domain.model.items.add.Item
import com.doubleclick.domain.model.items.get.Items
import com.doubleclick.domain.model.order.add.Order
import com.doubleclick.domain.model.sizes.Sizes
import com.doubleclick.domain.model.table.Tables
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface RestaurantRepo {

    //auth
    suspend fun login(login: Login): Response<LoginCallback>

    suspend fun register(register: Register): Response<LoginCallback>

    suspend fun sendOtp(email: Email): Response<Message>

    suspend fun confirmCode(o_t_p_Code: OTPCode): Response<Message>

    suspend fun resetPassword(resetPassword: ResetPassword): Response<Message>

    //countries
    suspend fun countries(): Response<String>

    //cities
    suspend fun cities(): Response<String>

    //provinces
    suspend fun provinces(): Response<String>

    //tables
    suspend fun tables(): Response<Tables>

    suspend fun setTables(name: String): Response<Message>

    //order
    suspend fun getOrders(): Response<String>

    suspend fun setOrders(jsonObject: JsonObject): Response<Message>

    //carts
    suspend fun getCarts(): Response<Carts>

    suspend fun setCarts(carts: Cart): Response<Message>

    suspend fun deleteCart(id: Int): Response<Message>

    suspend fun updateCart(number: Int, id: Int): Response<Message>

    //size
    suspend fun getSizes(): Response<String>

    suspend fun setSizes(sizes: Sizes): Response<String>

    //category
    suspend fun getCategories(): Response<Categories>

    suspend fun setCategories(name: String, image: MultipartBody.Part): Response<Message>

    //items
    suspend fun getItems(): Response<Items>

    suspend fun setItems(item: Item): Response<Message>

    suspend fun setImageItem(
        id: Int,
        item_image: MultipartBody.Part
    ): Response<Message>


}