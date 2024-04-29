package com.doubleclick.rovleapp.feature.profile.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.profile.ProfileRepository
import com.doubleclick.rovleapp.feature.profile.data.points.PointsData
import javax.inject.Inject

class ShowPointsUseCase @Inject constructor(private val showPointsRepository: ProfileRepository) :
    UseCase<List<PointsData>, UseCase.None>() {

    override suspend fun run(params: None) = showPointsRepository.showPoints()
}