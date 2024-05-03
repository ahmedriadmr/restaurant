package com.doubleclick.restaurant.feature.home.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.home.HomeRepository
import com.doubleclick.restaurant.feature.home.data.Categories.Categories

import javax.inject.Inject

class CategoriesUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<List<Categories>, UseCase.None>() {

    override suspend fun run(params: None) = repository.getCategories()


}