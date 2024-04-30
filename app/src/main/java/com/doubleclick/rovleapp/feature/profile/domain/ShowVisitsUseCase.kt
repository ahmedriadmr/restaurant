package com.doubleclick.restaurant.feature.profile.domain

import com.doubleclick.restaurant.core.interactor.UseCase
import com.doubleclick.restaurant.feature.profile.ProfileRepository
import com.doubleclick.restaurant.feature.profile.data.visits.VisitsData
import javax.inject.Inject

class ShowVisitsUseCase @Inject constructor(private val showVisitsRepository: ProfileRepository) :
    UseCase<List<VisitsData>, UseCase.None>() {

    override suspend fun run(params: None) = showVisitsRepository.showVisits()
}