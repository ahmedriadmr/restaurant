package com.doubleclick.restaurant.feature.profile.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.profile.ProfileRepository
import com.doubleclick.restaurant.feature.profile.data.points.PointsData
import javax.inject.Inject

class ShowPointsUseCase @Inject constructor(private val showPointsRepository: ProfileRepository) :
    UseCase<List<PointsData>, UseCase.None>() {

    override suspend fun run(params: None) = showPointsRepository.showPoints()
}