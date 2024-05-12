package com.doubleclick.restaurant.feature.admin.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.admin.AdminRepository
import com.doubleclick.restaurant.feature.admin.data.listItems.ItemsData
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(private val repository: AdminRepository) :
    UseCase<List<ItemsData>, UseCase.None>() {

    override suspend fun run(params: None) = repository.getItems()


}