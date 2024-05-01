package com.doubleclick.domain.usecase

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
import com.doubleclick.domain.repo.RestaurantRepo
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class GetData(private val restaurantRepo: RestaurantRepo) {

    suspend fun login(login: Login): Response<LoginCallback> = restaurantRepo.login(login)

    suspend fun register(register: Register): Response<LoginCallback> =
        restaurantRepo.register(register)

    suspend fun sendOtp(email: Email): Response<Message> = restaurantRepo.sendOtp(email)

    suspend fun confirmCode(o_t_PCode: OTPCode): Response<Message> =
        restaurantRepo.confirmCode(o_t_PCode)

    suspend fun resetPassword(resetPassword: ResetPassword): Response<Message> =
        restaurantRepo.resetPassword(resetPassword)

    suspend fun countries(): Response<String> = restaurantRepo.countries()

    suspend fun cities(): Response<String> = restaurantRepo.cities()

    suspend fun provinces(): Response<String> = restaurantRepo.provinces()

    suspend fun tables(): Response<Tables> = restaurantRepo.tables()

    suspend fun setTables(name: String): Response<Message> = restaurantRepo.setTables(name)

    suspend fun getOrders(): Response<String> = restaurantRepo.getOrders()

    suspend fun setOrders(jsonObject: JsonObject): Response<Message> = restaurantRepo.setOrders(jsonObject)

    suspend fun getSizes(): Response<String> = restaurantRepo.getSizes()

    suspend fun setSizes(sizes: Sizes): Response<String> = restaurantRepo.setSizes(sizes)

    suspend fun getCategories(): Response<Categories> = restaurantRepo.getCategories()

    suspend fun setCategories(name: String, image: MultipartBody.Part): Response<Message> =
        restaurantRepo.setCategories(name, image)

    suspend fun getCarts(): Response<Carts> = restaurantRepo.getCarts()

    suspend fun setCarts(carts: Cart): Response<Message> = restaurantRepo.setCarts(carts)

    suspend fun deleteCart(id: Int): Response<Message> = restaurantRepo.deleteCart(id)

    suspend fun updateCart(number: Int, id: Int): Response<Message> =
        restaurantRepo.updateCart(number, id)


    suspend fun getItems(): Response<Items> = restaurantRepo.getItems()

    suspend fun setItems(item: Item): Response<Message> = restaurantRepo.setItems(item)

    suspend fun setImageItem(
        id: Int,
        item_image: MultipartBody.Part
    ): Response<Message> = restaurantRepo.setImageItem(id, item_image)

}