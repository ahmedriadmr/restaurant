package com.doubleclick.rovleapp.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.home.HomeRepository
import com.doubleclick.rovleapp.feature.home.data.Categories
import javax.inject.Inject

class CategoriesUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<List<Categories>, UseCase.None>() {

    override suspend fun run(params: None) = repository.getCategories()


}